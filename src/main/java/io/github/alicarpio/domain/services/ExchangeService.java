package io.github.alicarpio.domain.services;

import io.github.alicarpio.domain.enums.CryptoType;
import io.github.alicarpio.domain.models.BuyOrder;
import io.github.alicarpio.domain.models.CryptoCurrencyData;
import io.github.alicarpio.domain.models.SellOrder;
import io.github.alicarpio.domain.validations.exceptions.CryptoNotFoundException;
import io.github.alicarpio.domain.validations.exceptions.IllegalAmountException;
import io.github.alicarpio.domain.validations.exceptions.InsufficientFundsException;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public interface ExchangeService {

    void placeSellOrder(SellOrder sellOrder) throws CryptoNotFoundException, IllegalAmountException;

    void placeBuyOrder(BuyOrder buyOrder) throws InsufficientFundsException;

    UUID getId();

    <T> T withFunds(Function<Map<CryptoType, CryptoCurrencyData>, ? extends T> callback);
}
