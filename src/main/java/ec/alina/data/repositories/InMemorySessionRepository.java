package ec.alina.data.repositories;

import ec.alina.domain.models.Session;
import ec.alina.domain.models.User;
import ec.alina.domain.repositories.SessionRepository;

import java.util.Objects;

public class InMemorySessionRepository implements SessionRepository {
    private Session session;

    @Override
    public void save(Session session) {
        this.session = session;
    }

    @Override
    public void remove() {
        this.session = null;
    }

    @Override
    public User getCurrentUser() {
        Objects.requireNonNull(session, "No hay una sesión activa");
        return session.getUser();
    }
}
