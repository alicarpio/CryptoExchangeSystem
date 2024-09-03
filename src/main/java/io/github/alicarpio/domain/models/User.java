package io.github.alicarpio.domain.models;

import io.github.alicarpio.Utils;

import java.util.UUID;

public final class User {
    private final UUID id;
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        this.id = Utils.generateUniqueId();
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getId() {
        return id;
    }
}
