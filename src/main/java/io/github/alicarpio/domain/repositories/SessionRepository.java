package io.github.alicarpio.domain.repositories;

import io.github.alicarpio.domain.models.Session;
import io.github.alicarpio.domain.models.User;

public interface SessionRepository {
    void save(Session session);

    void remove();

    User getCurrentUser();
}
