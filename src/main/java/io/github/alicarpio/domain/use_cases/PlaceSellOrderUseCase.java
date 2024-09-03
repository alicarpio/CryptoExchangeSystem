package io.github.alicarpio.domain.use_cases;

import io.github.alicarpio.domain.enums.CryptoType;

import io.github.alicarpio.domain.models.SellOrder;
import io.github.alicarpio.domain.repositories.SessionRepository;
import io.github.alicarpio.domain.services.ExchangeService;
import io.github.alicarpio.domain.validations.Validator;
import io.github.alicarpio.domain.validations.exceptions.ValidationException;

import java.math.BigDecimal;

public class PlaceSellOrderUseCase {
    private final ExchangeService exchangeService;
    private final Validator<SellOrder> validator;
    private final SessionRepository session;


    public PlaceSellOrderUseCase(ExchangeService exchangeService, Validator<SellOrder> validator, SessionRepository session) {
        this.session = session;
        this.exchangeService = exchangeService;
        this.validator = validator;
    }

    public void invoke(CryptoType cryptoType, BigDecimal amount, BigDecimal minimumPrice) throws ValidationException{
        SellOrder sellOrder = new SellOrder(cryptoType, amount, minimumPrice,session.getCurrentUser().getId());
        validator.validate(sellOrder);
        exchangeService.placeSellOrder(sellOrder);
    }
}
