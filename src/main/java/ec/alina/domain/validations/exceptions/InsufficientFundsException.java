package ec.alina.domain.validations.exceptions;

public class InsufficientFundsException extends ValidationException {
    public InsufficientFundsException() {
        super("Insufficient funds for the transaction");
    }
}
