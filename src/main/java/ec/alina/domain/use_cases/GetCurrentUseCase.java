package ec.alina.domain.use_cases;

import ec.alina.domain.models.User;
import ec.alina.domain.repositories.SessionRepository;

public class GetCurrentUseCase {

    private final SessionRepository sessionRepository;

    public GetCurrentUseCase(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public User invoke() {
        return sessionRepository.getCurrentUser();
    }
}
