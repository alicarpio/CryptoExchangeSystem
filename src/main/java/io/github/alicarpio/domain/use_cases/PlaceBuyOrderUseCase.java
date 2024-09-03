package io.github.alicarpio.domain.use_cases;

import io.github.alicarpio.domain.enums.CryptoType;
import io.github.alicarpio.domain.models.BuyOrder;
import io.github.alicarpio.domain.repositories.SessionRepository;
import io.github.alicarpio.domain.services.ExchangeService;
import io.github.alicarpio.domain.validations.Validator;
import io.github.alicarpio.domain.validations.exceptions.ValidationException;

import java.math.BigDecimal;

public class PlaceBuyOrderUseCase {
    private final ExchangeService exchangeService;
    private final Validator<BuyOrder> validator;
    private final SessionRepository session;

    public PlaceBuyOrderUseCase(ExchangeService exchangeService, Validator<BuyOrder> validator, SessionRepository session) {
        this.exchangeService = exchangeService;
        this.validator = validator;
        this.session = session;
    }

    public void invoke(CryptoType cryptoType, BigDecimal amount, BigDecimal maximumPrice) throws ValidationException {
        BuyOrder buyOrder = new BuyOrder(cryptoType,amount,maximumPrice,session.getCurrentUser().getId());
        validator.validate(buyOrder);
        exchangeService.placeBuyOrder(buyOrder);
    }
}
