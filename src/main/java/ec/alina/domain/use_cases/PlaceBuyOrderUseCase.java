package ec.alina.domain.use_cases;

import ec.alina.domain.enums.CryptoType;
import ec.alina.domain.models.BuyOrder;
import ec.alina.domain.repositories.SessionRepository;
import ec.alina.domain.services.ExchangeService;
import ec.alina.domain.validations.Validator;
import ec.alina.domain.validations.exceptions.ValidationException;

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
