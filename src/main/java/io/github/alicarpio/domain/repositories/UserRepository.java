package io.github.alicarpio.domain.repositories;

import io.github.alicarpio.domain.models.User;

public interface UserRepository {
    void save(User user);

    User findByEmail(String email);
}



