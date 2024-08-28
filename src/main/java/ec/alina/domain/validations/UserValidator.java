package ec.alina.domain.validations;

import ec.alina.domain.models.User;
import ec.alina.domain.validations.exceptions.InvalidEmailException;
import ec.alina.domain.validations.exceptions.PasswordTooShortException;
import ec.alina.domain.validations.exceptions.ValidationException;

public class UserValidator implements Validator<User> {
    public static final int MIN_PASSWORD_LENGTH = 8;

    @Override
    public void validate(User user) throws ValidationException {
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
    }

    private void validateEmail(String email) throws InvalidEmailException {
        if (email == null || !email.contains("@")) {
            throw new InvalidEmailException();
        }
    }

    private void validatePassword(String password) throws PasswordTooShortException {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new PasswordTooShortException(MIN_PASSWORD_LENGTH);
        }
    }
}
