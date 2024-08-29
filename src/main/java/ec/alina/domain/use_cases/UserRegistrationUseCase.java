package ec.alina.domain.use_cases;

import ec.alina.domain.models.User;
import ec.alina.domain.repositories.UserRepository;
import ec.alina.domain.validations.Validator;
import ec.alina.domain.validations.exceptions.ValidationException;

public class UserRegistrationUseCase {
    private final UserRepository users;
    private final Validator<User> validator;

    public UserRegistrationUseCase(UserRepository users, Validator<User> validator) {
        this.users = users;
        this.validator = validator;
    }

    public User invoke(String name, String email, String password) throws ValidationException {

        User user = new User(name, email, password);
        validator.validate(user);
        users.save(user);

        return user;
    }
}
