package ec.alina.domain.services;

import ec.alina.domain.enums.CryptoType;
import ec.alina.domain.models.BuyOrder;
import ec.alina.domain.models.CryptoCurrencyData;
import ec.alina.domain.models.SellOrder;
import ec.alina.domain.validations.exceptions.CryptoNotFoundException;
import ec.alina.domain.validations.exceptions.IllegalAmountException;
import ec.alina.domain.validations.exceptions.InsufficientFundsException;

import java.util.Map;
import java.util.UUID;

public interface ExchangeService {

    void placeSellOrder(SellOrder sellOrder) throws CryptoNotFoundException, IllegalAmountException;

    void placeBuyOrder(BuyOrder buyOrder) throws InsufficientFundsException;

    Map<CryptoType, CryptoCurrencyData> getFunds();

    UUID getId();
}
