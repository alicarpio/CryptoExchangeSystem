package ec.alina.domain.repositories;

import ec.alina.domain.models.Session;
import ec.alina.domain.models.User;

public interface SessionRepository {
    void save(Session session);

    void remove();

    User getCurrentUser();
}
