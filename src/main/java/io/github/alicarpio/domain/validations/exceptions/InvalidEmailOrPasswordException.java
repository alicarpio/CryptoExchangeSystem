package io.github.alicarpio.domain.validations.exceptions;

public class InvalidEmailOrPasswordException extends ValidationException {
    public InvalidEmailOrPasswordException() {
        super("The email or password are incorrect");
    }
}
