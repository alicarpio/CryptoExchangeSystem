package ec.alina.domain.services;

import ec.alina.domain.enums.CryptoType;
import ec.alina.domain.models.BuyOrder;
import ec.alina.domain.models.CryptoCurrencyData;
import ec.alina.domain.models.Exchange;
import ec.alina.domain.models.SellOrder;
import ec.alina.domain.repositories.OrderFulfilledCallback;

import java.util.Map;
import java.util.UUID;

public interface ExchangeService {

    void placeSellOrder(SellOrder sellOrder, OrderFulfilledCallback callback);

    void placeBuyOrder(BuyOrder buyOrder, OrderFulfilledCallback callback);

    Map<CryptoType, CryptoCurrencyData> getFunds();

    UUID getId();
}
