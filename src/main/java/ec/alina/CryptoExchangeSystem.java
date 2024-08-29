package ec.alina;

import ec.alina.data.repositories.InMemorySessionRepository;
import ec.alina.data.repositories.InMemoryUserRepository;
import ec.alina.domain.config.BootAdapter;
import ec.alina.domain.repositories.SessionRepository;
import ec.alina.domain.repositories.UserRepository;
import ec.alina.domain.use_cases.UserLoginUseCase;
import ec.alina.domain.use_cases.UserLogoutUseCase;
import ec.alina.domain.use_cases.UserRegistrationUseCase;
import ec.alina.domain.validations.UserValidator;
import ec.alina.ui.ConsoleAdapter;

public class CryptoExchangeSystem {
    public CryptoExchangeSystem() {
    }

    private static void boot() {
        UserRepository userRepository = new InMemoryUserRepository();
        SessionRepository sessionRepository = new InMemorySessionRepository();

        UserRegistrationUseCase userRegistrationUseCase = new UserRegistrationUseCase(userRepository, new UserValidator());
        UserLoginUseCase userLoginUseCase = new UserLoginUseCase(userRepository, sessionRepository);
        UserLogoutUseCase userLogoutUseCase = new UserLogoutUseCase(sessionRepository);

        BootAdapter bootAdapter = new ConsoleAdapter(userRegistrationUseCase, userLoginUseCase, userLogoutUseCase);
        bootAdapter.boot();
    }

    public static void main(String[] args) {
        boot();
    }
}