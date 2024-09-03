package io.github.alicarpio.data.repositories;

import io.github.alicarpio.domain.models.Session;
import io.github.alicarpio.domain.models.User;
import io.github.alicarpio.domain.repositories.SessionRepository;

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
