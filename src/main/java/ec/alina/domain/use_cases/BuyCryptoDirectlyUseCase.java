package ec.alina.domain.use_cases;

import ec.alina.CryptoExchangeSystem;
import ec.alina.domain.enums.CryptoType;
import ec.alina.domain.enums.TransactionType;
import ec.alina.domain.models.*;
import ec.alina.domain.repositories.SessionRepository;
import ec.alina.domain.repositories.TransactionRepository;
import ec.alina.domain.repositories.WalletRepository;
import ec.alina.domain.validations.exceptions.InsufficientCryptoQuantityException;
import ec.alina.domain.validations.exceptions.InsufficientFundsException;
import ec.alina.domain.validations.exceptions.InvalidCryptoType;
import ec.alina.domain.models.Transaction;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public class BuyCryptoDirectlyUseCase {
    private final WalletRepository wallets;
    private final TransactionRepository transactions;
    private final SessionRepository session;

    public BuyCryptoDirectlyUseCase(WalletRepository wallets, TransactionRepository transactions, SessionRepository session) {
        this.wallets = wallets;
        this.transactions = transactions;
        this.session = session;
    }

    public void invoke(CryptoType cryptoType, BigDecimal amount) throws InsufficientFundsException, InvalidCryptoType, InsufficientCryptoQuantityException {
        UUID currentUserId = session.getCurrentUser().getId();
        Wallet userWallet = wallets.findWalletByUserId(currentUserId);
        BigDecimal userFunds = userWallet.getFiatBalance();
        Map<CryptoType, CryptoCurrencyData> exchangeInitialFunds = CryptoExchangeSystem.getExchange().getInitialFunds();
        BigDecimal cryptoPrice = exchangeInitialFunds.get(cryptoType).getPrice();

        if (userFunds.compareTo(cryptoPrice) < 0){
            throw new InsufficientFundsException();
        }

        if (!exchangeInitialFunds.containsKey(CryptoType.BTC)){
            throw new InvalidCryptoType();
        }

        if (exchangeInitialFunds.get(cryptoType).getQuantity().compareTo(amount) < 0){
            throw new InsufficientCryptoQuantityException();
        }

        userWallet.setFiatBalance(userFunds.subtract(cryptoPrice));
        userWallet.setFiatBalance(userWallet.getFiatBalance().subtract(cryptoPrice));
        userWallet.getCrytoHoldings().put(cryptoType, userWallet.getCrytoHoldings().getOrDefault(cryptoType, BigDecimal.ZERO).add(amount));

        CryptoCurrencyData cryptoData = exchangeInitialFunds.get(cryptoType);
        BigDecimal newExchangeQuantity = exchangeInitialFunds.get(cryptoType).getQuantity().subtract(amount);
        cryptoData.setQuantity(newExchangeQuantity);

        Transaction buyTransaction = new Transaction(cryptoType, amount, cryptoPrice, TransactionType.BUY,currentUserId,currentUserId);
        transactions.save(currentUserId, buyTransaction);
    }
}
