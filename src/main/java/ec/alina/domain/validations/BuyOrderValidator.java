package ec.alina.domain.validations;

import ec.alina.domain.enums.CryptoType;
import ec.alina.domain.models.BuyOrder;
import ec.alina.domain.validations.exceptions.InvalidCryptoType;
import ec.alina.domain.validations.exceptions.ValidationException;

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