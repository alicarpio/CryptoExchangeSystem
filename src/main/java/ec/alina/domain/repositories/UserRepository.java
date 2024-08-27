package ec.alina.domain.repositories;

import ec.alina.domain.models.User;

public interface UserRepository {
    User create(String name, String email, String password);

    void save(User user);

    User findByEmail(String email);
}



