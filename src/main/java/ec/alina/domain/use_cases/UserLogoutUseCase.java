package ec.alina.domain.use_cases;

import ec.alina.domain.repositories.SessionRepository;

public class UserLogoutUseCase {
    private final SessionRepository sessions;

    public UserLogoutUseCase(SessionRepository sessions) {
        this.sessions = sessions;
    }

    public void invoke() {
        sessions.remove();
    }
}
