package io.github.alicarpio.domain.validations;

import io.github.alicarpio.domain.validations.exceptions.IllegalAmountException;

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
}
