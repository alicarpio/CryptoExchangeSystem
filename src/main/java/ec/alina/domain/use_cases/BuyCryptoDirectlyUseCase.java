package ec.alina.domain.use_cases;

import ec.alina.domain.enums.CryptoType;
import ec.alina.domain.enums.TransactionType;
import ec.alina.domain.models.*;
import ec.alina.domain.services.ExchangeService;
import ec.alina.domain.repositories.SessionRepository;
import ec.alina.domain.repositories.TransactionRepository;
import ec.alina.domain.repositories.WalletRepository;
import ec.alina.domain.validations.exceptions.InsufficientCryptoQuantityException;
import ec.alina.domain.validations.exceptions.InsufficientFundsException;
import ec.alina.domain.validations.exceptions.InvalidCryptoType;
import ec.alina.domain.models.Transaction;

import java.math.BigDecimal;
import java.util.UUID;

public class BuyCryptoDirectlyUseCase {
    private final WalletRepository wallets;
    private final TransactionRepository transactions;
    private final SessionRepository session;
    private final ExchangeService exchangeService;

    public BuyCryptoDirectlyUseCase(WalletRepository wallets, TransactionRepository transactions, SessionRepository session, ExchangeService exchangeService) {
        this.wallets = wallets;
        this.transactions = transactions;
        this.session = session;
        this.exchangeService = exchangeService;
    }

    public void invoke(CryptoType cryptoType, BigDecimal amount) throws InsufficientFundsException, InvalidCryptoType, InsufficientCryptoQuantityException {
        UUID currentUserId = session.getCurrentUser().getId();
        Wallet userWallet = wallets.findWalletByUserId(currentUserId);
        BigDecimal userFunds = userWallet.getFiatBalance();
        var funds = exchangeService.getFunds();

        if (!funds.containsKey(cryptoType)){
            throw new InvalidCryptoType();
        }

        CryptoCurrencyData cryptoData = funds.get(cryptoType);

        if (cryptoData.getQuantity().compareTo(amount) < 0){
            throw new InsufficientCryptoQuantityException();
        }

        BigDecimal cryptoPrice = cryptoData.getPrice().multiply(amount);

        if (userFunds.compareTo(cryptoPrice) < 0){
            throw new InsufficientFundsException();
        }

        userWallet.setFiatBalance(userWallet.getFiatBalance().subtract(cryptoPrice));
        userWallet.getCrytoHoldings().merge(cryptoType, amount, BigDecimal::add);

        BigDecimal newExchangeQuantity = cryptoData.getQuantity().subtract(amount);
        cryptoData.setQuantity(newExchangeQuantity);

        Transaction buyTransaction = new Transaction(cryptoType, amount, cryptoPrice, TransactionType.BUY,currentUserId,exchangeService.getId());
        transactions.save(currentUserId, buyTransaction);
    }
}
