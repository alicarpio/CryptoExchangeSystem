package io.github.alicarpio.domain.validations;

import io.github.alicarpio.domain.models.User;
import io.github.alicarpio.domain.validations.exceptions.InvalidEmailException;
import io.github.alicarpio.domain.validations.exceptions.PasswordTooShortException;
import io.github.alicarpio.domain.validations.exceptions.ValidationException;

public class UserValidator implements Validator<User> {
    public static final int MIN_PASSWORD_LENGTH = 8;

    @Override
    public void validate(User user) throws ValidationException {
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
    }

    private void validateEmail(String email) throws InvalidEmailException {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new InvalidEmailException();
        }
    }

    private void validatePassword(String password) throws PasswordTooShortException {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new PasswordTooShortException(MIN_PASSWORD_LENGTH);
        }
    }
}
