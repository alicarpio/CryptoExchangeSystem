package ec.alina.domain.validations.exceptions;

public class InsufficientCryptoQuantityException extends ValidationException{
    public InsufficientCryptoQuantityException() {
        super("Insufficient quantity of crypto currency");
    }
}
