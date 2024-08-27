package ec.alina.domain.use_cases;

import ec.alina.domain.models.User;
import ec.alina.domain.repositories.UserRepository;

public class UserRegistrationUseCase {
    private final UserRepository users;

    public UserRegistrationUseCase(UserRepository users) {
        this.users = users;
    }

    public User invoke(String name, String email, String password) {
        return users.create(name, email, password);
    }
}
