package io.github.alicarpio;

import io.github.alicarpio.data.repositories.InMemorySessionRepository;
import io.github.alicarpio.data.repositories.InMemoryTransactionRepository;
import io.github.alicarpio.data.repositories.InMemoryUserRepository;
import io.github.alicarpio.data.repositories.InMemoryWalletRepository;
import io.github.alicarpio.domain.config.BootAdapter;
import io.github.alicarpio.domain.enums.CryptoType;
import io.github.alicarpio.domain.models.CryptoCurrencyData;
import io.github.alicarpio.domain.models.Exchange;
import io.github.alicarpio.domain.repositories.SessionRepository;
import io.github.alicarpio.domain.repositories.TransactionRepository;
import io.github.alicarpio.domain.repositories.UserRepository;
import io.github.alicarpio.domain.repositories.WalletRepository;
import io.github.alicarpio.domain.services.ExchangeService;
import io.github.alicarpio.domain.use_cases.*;
import io.github.alicarpio.domain.validations.BuyOrderValidator;
import io.github.alicarpio.domain.validations.SellOrderValidator;
import io.github.alicarpio.domain.validations.UserValidator;
import io.github.alicarpio.services.ExchangeServiceImpl;
import io.github.alicarpio.task.CryptoFluctuationTask;
import io.github.alicarpio.ui.ConsoleAdapter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CryptoExchangeSystem {
    private static Exchange exchange;

    public CryptoExchangeSystem() {
    }

    private static void initializeExchange() {
        Map<CryptoType,CryptoCurrencyData> initialFunds = new HashMap<>();
        initialFunds.put(CryptoType.BTC, new CryptoCurrencyData(new BigDecimal("50000"), new BigDecimal("100")));
        initialFunds.put(CryptoType.ETH, new CryptoCurrencyData(new BigDecimal("3000"), new BigDecimal("50")));
        initialFunds.put(CryptoType.LTC, new CryptoCurrencyData(new BigDecimal("1000"), new BigDecimal("25")));
        exchange = new Exchange(initialFunds);
    }

    private static void boot() {
        initializeExchange();

        UserRepository userRepository = new InMemoryUserRepository();
        SessionRepository sessionRepository = new InMemorySessionRepository();
        WalletRepository walletRepository = new InMemoryWalletRepository();
        TransactionRepository transactionRepository = new InMemoryTransactionRepository();
        ExchangeService exchangeService = new ExchangeServiceImpl(exchange,walletRepository,transactionRepository);

        UserRegistrationUseCase userRegistrationUseCase = new UserRegistrationUseCase(userRepository, new UserValidator());
        UserLoginUseCase userLoginUseCase = new UserLoginUseCase(userRepository, sessionRepository);
        UserLogoutUseCase userLogoutUseCase = new UserLogoutUseCase(sessionRepository);
        GetCurrentUseCase getCurrentUseCase = new GetCurrentUseCase(sessionRepository);
        ViewWalletBalanceUseCase viewWalletBalanceUseCase = new ViewWalletBalanceUseCase(walletRepository);
        WalletRegistrationUseCase walletRegistrationUseCase = new WalletRegistrationUseCase(walletRepository);
        DepositMoneyUseCase depositMoneyUseCase = new DepositMoneyUseCase(walletRepository);
        ViewTransactionHistoryUseCase viewTransactionHistoryUseCase = new ViewTransactionHistoryUseCase(transactionRepository);
        BuyCryptoDirectlyUseCase buyCryptoDirectlyUseCase = new BuyCryptoDirectlyUseCase(walletRepository, transactionRepository, sessionRepository, exchangeService);
        PlaceBuyOrderUseCase placeBuyOrderUseCase = new PlaceBuyOrderUseCase(exchangeService, new BuyOrderValidator(), sessionRepository);
        PlaceSellOrderUseCase placeSellOrderUseCase = new PlaceSellOrderUseCase(exchangeService, new SellOrderValidator(), sessionRepository);

        BootAdapter bootAdapter = new ConsoleAdapter
                (
                        userRegistrationUseCase,
                        userLoginUseCase,
                        userLogoutUseCase,
                        getCurrentUseCase,
                        walletRegistrationUseCase,
                        viewWalletBalanceUseCase,
                        depositMoneyUseCase,
                        viewTransactionHistoryUseCase,
                        buyCryptoDirectlyUseCase,
                        placeBuyOrderUseCase,
                        placeSellOrderUseCase
                );

        new CryptoFluctuationTask(exchange).runForeverAsync();

        bootAdapter.boot();
    }

    public static Exchange getExchange() {
        return exchange;
    }

    public static void main(String[] args) {
        boot();
    }
}