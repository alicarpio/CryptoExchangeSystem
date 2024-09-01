package ec.alina.domain.validations.exceptions;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException() {
        super("Insufficient funds for the transaction");
    }
}
