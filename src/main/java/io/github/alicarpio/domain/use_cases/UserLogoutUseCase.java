package io.github.alicarpio.domain.use_cases;

import io.github.alicarpio.domain.repositories.SessionRepository;

public class UserLogoutUseCase {
    private final SessionRepository sessions;

    public UserLogoutUseCase(SessionRepository sessions) {
        this.sessions = sessions;
    }

    public void invoke() {
        sessions.remove();
    }
}
