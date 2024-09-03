package io.github.alicarpio.domain.validations;

import io.github.alicarpio.domain.enums.CryptoType;
import io.github.alicarpio.domain.models.SellOrder;
import io.github.alicarpio.domain.validations.exceptions.InvalidCryptoType;
import io.github.alicarpio.domain.validations.exceptions.ValidationException;

import java.util.EnumSet;

public class SellOrderValidator implements Validator<SellOrder> {
    private final NumberValidator numberValidator = new NumberValidator();

    @Override
    public void validate(SellOrder sellOrder) throws ValidationException {
        validateCryptoType(sellOrder.getCryptoCurrency());
        numberValidator.validate(sellOrder.getAmount(), "Amount");
        numberValidator.validate(sellOrder.getMinimumPrice(), "Minimum price");
    }

    private void validateCryptoType(CryptoType cryptoType) throws InvalidCryptoType {
        if (cryptoType == null || !EnumSet.allOf(CryptoType.class).contains(cryptoType)) {
            throw new InvalidCryptoType();
        }
    }
}
