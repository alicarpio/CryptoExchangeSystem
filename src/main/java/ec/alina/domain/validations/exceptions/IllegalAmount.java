package ec.alina.domain.validations.exceptions;

public class IllegalAmount extends ValidationException {
    public IllegalAmount() {
        super("Amount must be greater than or equal to zero");
    }
}
