package io.github.alicarpio.domain.validations.exceptions;

public class InvalidEmailException extends ValidationException {
    public InvalidEmailException() {
        super("The email is not valid");
    }
}
