package ec.alina.domain.use_cases;

import ec.alina.domain.enums.CryptoType;
import ec.alina.domain.models.BuyOrder;

import ec.alina.domain.models.SellOrder;
import ec.alina.domain.repositories.SessionRepository;
import ec.alina.domain.services.ExchangeService;
import ec.alina.domain.validations.Validator;
import ec.alina.domain.validations.exceptions.ValidationException;

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
