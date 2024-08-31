package ec.alina.domain.validations;

import ec.alina.domain.validations.exceptions.IllegalAmountException;

import java.math.BigDecimal;

public class NumberValidator implements Validator<BigDecimal> {

    @Override
    public void validate(BigDecimal value) throws IllegalAmountException {
        validate(value, null);
    }

    public void validate(BigDecimal value, String fieldName) throws IllegalAmountException {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalAmountException(fieldName + " must be greater than 0.");
        }
    }

    public static boolean isValidNumber(String str) {
        return InputValidator.isValidNumber(str);
    }
}
