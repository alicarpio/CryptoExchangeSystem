package ec.edu.espol.domain.repositories;

import ec.edu.espol.domain.models.User;

public interface UserRepository {
    User create(String name, String email, String password);

    void save(User user);

    User findByEmail(String email);
}



