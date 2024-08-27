package ec.alina.data.repositories;

import ec.alina.domain.models.Session;
import ec.alina.domain.models.User;
import ec.alina.domain.repositories.SessionRepository;

public class InMemorySessionRepository implements SessionRepository {
    private Session session;

    @Override
    public Session create(User user) {
        return new Session(user);
    }

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
        return session.getUser();
    }
}
