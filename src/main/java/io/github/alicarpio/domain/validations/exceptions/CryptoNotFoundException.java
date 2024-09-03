package io.github.alicarpio.domain.validations.exceptions;

public class CryptoNotFoundException extends ValidationException {
    public CryptoNotFoundException() {
        super("Missing crypto currency in wallet");
    }
}
