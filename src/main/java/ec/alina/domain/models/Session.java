package ec.alina.domain.models;

import ec.alina.Utils;

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
