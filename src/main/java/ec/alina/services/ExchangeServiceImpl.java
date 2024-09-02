package ec.alina.services;

import ec.alina.domain.enums.CryptoType;
import ec.alina.domain.enums.TransactionType;
import ec.alina.domain.models.*;
import ec.alina.domain.repositories.TransactionRepository;
import ec.alina.domain.repositories.WalletRepository;
import ec.alina.domain.services.ExchangeService;


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
    public void placeSellOrder(SellOrder sellOrder) {

        CryptoType cryptoType = sellOrder.getCryptoCurrency();
        BigDecimal amount = sellOrder.getAmount();
        BigDecimal price = sellOrder.getMinimumPrice();
        UUID sellerId = sellOrder.getUserId();
        Wallet sellerWallet = wallets.findWalletByUserId(sellerId);

        for(BuyOrder buyOrder: exchange.getBuyOrders()){
            if(buyOrder.getAmount().equals(amount) && price.compareTo(sellOrder.getMinimumPrice()) >= 0){
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
    public void placeBuyOrder(BuyOrder buyOrder) {

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
