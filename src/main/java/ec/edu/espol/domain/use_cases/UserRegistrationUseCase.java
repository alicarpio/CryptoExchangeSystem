package ec.edu.espol.domain.use_cases;

import ec.edu.espol.domain.models.User;
import ec.edu.espol.domain.repositories.UserRepository;

public class UserRegistrationUseCase {
    private final UserRepository users;

    public UserRegistrationUseCase(UserRepository users) {
        this.users = users;
    }

    public User invoke(String name, String email, String password) {
        return users.create(name, email, password);
    }
}
