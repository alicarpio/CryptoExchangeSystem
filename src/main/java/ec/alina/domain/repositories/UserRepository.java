package ec.alina.domain.repositories;

import ec.alina.domain.models.User;

public interface UserRepository {
    void save(User user);

    User findByEmail(String email);
}



