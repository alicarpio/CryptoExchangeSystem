package ec.edu.espol.data.repositories;

import ec.edu.espol.domain.models.User;
import ec.edu.espol.domain.repositories.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryUserRepository implements UserRepository {
    Map<UUID, User> users;

    public InMemoryUserRepository() {
        this.users = new HashMap<>();
    }

    @Override
    public User create(String name, String email, String password) {
        return new User(name, email, password);
    }

    @Override
    public void save(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }
}
