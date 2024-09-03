package io.github.alicarpio.domain.use_cases;

import io.github.alicarpio.domain.models.User;
import io.github.alicarpio.domain.repositories.UserRepository;
import io.github.alicarpio.domain.validations.Validator;
import io.github.alicarpio.domain.validations.exceptions.ValidationException;

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
