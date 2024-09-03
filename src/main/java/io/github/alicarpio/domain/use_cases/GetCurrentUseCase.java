package io.github.alicarpio.domain.use_cases;

import io.github.alicarpio.domain.models.User;
import io.github.alicarpio.domain.repositories.SessionRepository;

public class GetCurrentUseCase {

    private final SessionRepository sessionRepository;

    public GetCurrentUseCase(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public User invoke() {
        return sessionRepository.getCurrentUser();
    }
}
