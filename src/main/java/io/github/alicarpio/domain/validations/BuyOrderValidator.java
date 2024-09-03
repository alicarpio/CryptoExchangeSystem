package io.github.alicarpio.domain.validations;

import io.github.alicarpio.domain.enums.CryptoType;
import io.github.alicarpio.domain.models.BuyOrder;
import io.github.alicarpio.domain.validations.exceptions.InvalidCryptoType;
import io.github.alicarpio.domain.validations.exceptions.ValidationException;

import java.util.EnumSet;

public class BuyOrderValidator implements Validator<BuyOrder> {
    private final NumberValidator numberValidator = new NumberValidator();

    @Override
    public void validate(BuyOrder buyOrder) throws ValidationException {
        validateCryptoType(buyOrder.getCryptoCurrency());
        numberValidator.validate(buyOrder.getAmount(), "Amount");
        numberValidator.validate(buyOrder.getMaximumPrice(), "Maximum price");
    }

    private void validateCryptoType(CryptoType cryptoType) throws InvalidCryptoType {
        if (cryptoType == null || !EnumSet.allOf(CryptoType.class).contains(cryptoType)) {
            throw new InvalidCryptoType();
        }
    }
}