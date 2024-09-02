package ec.alina;

import ec.alina.data.repositories.InMemorySessionRepository;
import ec.alina.data.repositories.InMemoryTransactionRepository;
import ec.alina.data.repositories.InMemoryUserRepository;
import ec.alina.data.repositories.InMemoryWalletRepository;
import ec.alina.domain.config.BootAdapter;
import ec.alina.domain.enums.CryptoType;
import ec.alina.domain.models.CryptoCurrencyData;
import ec.alina.domain.models.Exchange;
import ec.alina.domain.repositories.SessionRepository;
import ec.alina.domain.repositories.TransactionRepository;
import ec.alina.domain.repositories.UserRepository;
import ec.alina.domain.repositories.WalletRepository;
import ec.alina.domain.services.ExchangeService;
import ec.alina.domain.use_cases.*;
import ec.alina.domain.validations.BuyOrderValidator;
import ec.alina.domain.validations.SellOrderValidator;
import ec.alina.domain.validations.UserValidator;
import ec.alina.services.ExchangeServiceImpl;
import ec.alina.ui.ConsoleAdapter;

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
        bootAdapter.boot();
    }

    public static Exchange getExchange() {
        return exchange;
    }

    public static void main(String[] args) {
        boot();
    }
}