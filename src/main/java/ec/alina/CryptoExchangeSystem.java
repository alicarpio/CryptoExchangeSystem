package ec.alina;

import ec.alina.data.repositories.InMemorySessionRepository;
import ec.alina.data.repositories.InMemoryTransactionRepository;
import ec.alina.data.repositories.InMemoryUserRepository;
import ec.alina.data.repositories.InMemoryWalletRepository;
import ec.alina.domain.config.BootAdapter;
import ec.alina.domain.enums.CrytoType;
import ec.alina.domain.models.Exchange;
import ec.alina.domain.repositories.SessionRepository;
import ec.alina.domain.repositories.TransactionRepository;
import ec.alina.domain.repositories.UserRepository;
import ec.alina.domain.repositories.WalletRepository;
import ec.alina.domain.use_cases.*;
import ec.alina.domain.validations.UserValidator;
import ec.alina.ui.ConsoleAdapter;

import java.util.HashMap;
import java.util.Map;

public class CryptoExchangeSystem {
    private static Exchange exchange;

    public CryptoExchangeSystem() {
    }

    private static void initializeExchange() {
        Map<CrytoType,Integer> initialFunds = new HashMap<>();
        initialFunds.put(CrytoType.BTC, 100);
        initialFunds.put(CrytoType.ETH, 50);
        initialFunds.put(CrytoType.LTC, 25);
        exchange = new Exchange(initialFunds);
    }

    private static void boot() {
        initializeExchange();

        UserRepository userRepository = new InMemoryUserRepository();
        SessionRepository sessionRepository = new InMemorySessionRepository();
        WalletRepository walletRepository = new InMemoryWalletRepository();
        TransactionRepository transactionRepository = new InMemoryTransactionRepository();

        UserRegistrationUseCase userRegistrationUseCase = new UserRegistrationUseCase(userRepository, new UserValidator());
        UserLoginUseCase userLoginUseCase = new UserLoginUseCase(userRepository, sessionRepository);
        UserLogoutUseCase userLogoutUseCase = new UserLogoutUseCase(sessionRepository);
        GetCurrentUseCase getCurrentUseCase = new GetCurrentUseCase(sessionRepository);
        ViewWalletBalanceUseCase viewWalletBalanceUseCase = new ViewWalletBalanceUseCase(walletRepository);
        WalletRegistrationUseCase walletRegistrationUseCase = new WalletRegistrationUseCase(walletRepository);
        DepositMoneyUseCase depositMoneyUseCase = new DepositMoneyUseCase(walletRepository);
        ViewTransactionHistoryUseCase viewTransactionHistoryUseCase = new ViewTransactionHistoryUseCase(transactionRepository);

        BootAdapter bootAdapter = new ConsoleAdapter
                (
                        userRegistrationUseCase,
                        userLoginUseCase,
                        userLogoutUseCase,
                        getCurrentUseCase,
                        walletRegistrationUseCase,
                        viewWalletBalanceUseCase,
                        depositMoneyUseCase,
                        viewTransactionHistoryUseCase
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