package io.github.alicarpio.domain.models;

import io.github.alicarpio.Utils;

import java.util.UUID;

public final class Session {
    private final UUID id;
    private final User user;

    public Session(User user) {
        this.id = Utils.generateUniqueId();
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
}
