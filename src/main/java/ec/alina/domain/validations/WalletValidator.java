package ec.alina.domain.validations;

import ec.alina.domain.models.Wallet;
import ec.alina.domain.validations.exceptions.ValidationException;

import java.math.BigDecimal;

public class WalletValidator implements Validator<Wallet> {

    @Override
    public void validate(Wallet entity) throws ValidationException {

    }

    private void validateAmount(BigDecimal amount) throws ValidationException {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Amount must be greater than or equal to zero");
        }


    }
}
