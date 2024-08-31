package ec.alina;

import ec.alina.data.repositories.InMemorySessionRepository;
import ec.alina.data.repositories.InMemoryUserRepository;
import ec.alina.data.repositories.InMemoryWalletRepository;
import ec.alina.domain.config.BootAdapter;
import ec.alina.domain.repositories.SessionRepository;
import ec.alina.domain.repositories.UserRepository;
import ec.alina.domain.repositories.WalletRepository;
import ec.alina.domain.use_cases.*;
import ec.alina.domain.validations.UserValidator;
import ec.alina.ui.ConsoleAdapter;

public class CryptoExchangeSystem {
    public CryptoExchangeSystem() {
    }

    private static void boot() {
        UserRepository userRepository = new InMemoryUserRepository();
        SessionRepository sessionRepository = new InMemorySessionRepository();
        WalletRepository walletRepository = new InMemoryWalletRepository();

        UserRegistrationUseCase userRegistrationUseCase = new UserRegistrationUseCase(userRepository, new UserValidator());
        UserLoginUseCase userLoginUseCase = new UserLoginUseCase(userRepository, sessionRepository);
        UserLogoutUseCase userLogoutUseCase = new UserLogoutUseCase(sessionRepository);
        GetCurrentUseCase getCurrentUseCase = new GetCurrentUseCase(sessionRepository);
        ViewWalletBalanceUseCase viewWalletBalanceUseCase = new ViewWalletBalanceUseCase(walletRepository);
        WalletRegistrationUseCase walletRegistrationUseCase = new WalletRegistrationUseCase(walletRepository);

        BootAdapter bootAdapter = new ConsoleAdapter(userRegistrationUseCase, userLoginUseCase, userLogoutUseCase, getCurrentUseCase, walletRegistrationUseCase, viewWalletBalanceUseCase);
        bootAdapter.boot();
    }

    public static void main(String[] args) {
        boot();
    }
}