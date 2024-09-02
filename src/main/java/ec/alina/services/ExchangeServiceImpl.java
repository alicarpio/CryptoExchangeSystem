package ec.alina.services;

import ec.alina.domain.enums.CryptoType;
import ec.alina.domain.enums.TransactionType;
import ec.alina.domain.models.*;
import ec.alina.domain.repositories.TransactionRepository;
import ec.alina.domain.repositories.WalletRepository;
import ec.alina.domain.services.ExchangeService;
import ec.alina.domain.validations.exceptions.CryptoNotFoundException;
import ec.alina.domain.validations.exceptions.IllegalAmountException;
import ec.alina.domain.validations.exceptions.InsufficientFundsException;


import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public class ExchangeServiceImpl implements ExchangeService {
    private final Exchange exchange;
    private final WalletRepository wallets;
    private final TransactionRepository transactions;

    public ExchangeServiceImpl(Exchange exchange, WalletRepository wallets, TransactionRepository transactions) {
        this.exchange = exchange;
        this.wallets = wallets;
        this.transactions = transactions;
    }

    @Override
    public void placeSellOrder(SellOrder sellOrder) throws CryptoNotFoundException, IllegalAmountException {

        CryptoType cryptoType = sellOrder.getCryptoCurrency();
        BigDecimal amount = sellOrder.getAmount();
        BigDecimal price = sellOrder.getMinimumPrice().multiply(amount);
        UUID sellerId = sellOrder.getUserId();
        Wallet sellerWallet = wallets.findWalletByUserId(sellerId);

        if (!sellerWallet.getCrytoHoldings().containsKey(cryptoType)){
            throw new CryptoNotFoundException();
        }

        if(sellerWallet.getCrytoHoldings().get(cryptoType).compareTo(amount) < 0){
            throw new IllegalAmountException("Insufficient amount of crypto in wallet");
        }

        for(BuyOrder buyOrder: exchange.getBuyOrders()){
            if(buyOrder.getAmount().equals(amount) && price.compareTo(buyOrder.getMaximumPrice().multiply(amount)) >= 0){
                Transaction sellTransaction = new Transaction(cryptoType,amount,price,TransactionType.SELL,sellerId,buyOrder.getUserId());
                transactions.save(sellerId,sellTransaction);
                Transaction buyTransaction = new Transaction(cryptoType,amount,price,TransactionType.BUY,sellerId,buyOrder.getUserId());
                transactions.save(buyOrder.getUserId(),buyTransaction);

                sellerWallet.setFiatBalance(sellerWallet.getFiatBalance().add(price));
                sellerWallet.getCrytoHoldings().merge(cryptoType,amount,BigDecimal::subtract);
                Wallet buyerWallet = wallets.findWalletByUserId(buyOrder.getUserId());
                buyerWallet.setFiatBalance(buyerWallet.getFiatBalance().subtract(price));
                buyerWallet.getCrytoHoldings().merge(cryptoType,amount,BigDecimal::add);

                exchange.getBuyOrders().remove(buyOrder);
            }else{
                exchange.getSellOrders().add(sellOrder);
            }
        }

    }

    @Override
    public void placeBuyOrder(BuyOrder buyOrder) throws InsufficientFundsException {
        CryptoType cryptoType = buyOrder.getCryptoCurrency();
        BigDecimal amount = buyOrder.getAmount();
        BigDecimal price = buyOrder.getMaximumPrice().multiply(amount);
        UUID buyerId = buyOrder.getUserId();
        Wallet buyerWallet = wallets.findWalletByUserId(buyerId);
        var funds = exchange.getInitialFunds().get(cryptoType).getPrice();

        if (funds.compareTo(price) < 0){
            throw new InsufficientFundsException();
        }

        for(SellOrder sellOrder: exchange.getSellOrders()){
            if(sellOrder.getAmount().equals(amount) && price.compareTo(sellOrder.getMinimumPrice()) <= 0){
                BigDecimal actualPrice = price.compareTo(sellOrder.getMinimumPrice().multiply(amount)) < 0 ? sellOrder.getMinimumPrice() : price;
                Transaction buyTransaction = new Transaction(cryptoType,amount,actualPrice,TransactionType.BUY,sellOrder.getUserId(),buyerId);
                transactions.save(buyerId,buyTransaction);
                Transaction sellTransaction = new Transaction(cryptoType,amount,actualPrice,TransactionType.SELL,sellOrder.getUserId(),buyerId);
                transactions.save(sellOrder.getUserId(),sellTransaction);

                buyerWallet.setFiatBalance(buyerWallet.getFiatBalance().subtract(actualPrice));
                buyerWallet.getCrytoHoldings().merge(cryptoType,amount,BigDecimal::add);
                Wallet sellerWallet = wallets.findWalletByUserId(sellOrder.getUserId());
                sellerWallet.setFiatBalance(sellerWallet.getFiatBalance().add(actualPrice));
                sellerWallet.getCrytoHoldings().merge(cryptoType,amount,BigDecimal::subtract);

                exchange.getSellOrders().remove(sellOrder);
            }else {
                exchange.getBuyOrders().add(buyOrder);
            }
        }
    }

    @Override
    public Map<CryptoType, CryptoCurrencyData> getFunds() {
        return exchange.getInitialFunds();
    }

    @Override
    public UUID getId() {
        return exchange.getId();
    }

}
