package ec.alina.domain.services;

import ec.alina.domain.enums.CryptoType;
import ec.alina.domain.models.BuyOrder;
import ec.alina.domain.models.CryptoCurrencyData;
import ec.alina.domain.models.SellOrder;

import java.util.Map;
import java.util.UUID;

public interface ExchangeService {

    void placeSellOrder(SellOrder sellOrder);

    void placeBuyOrder(BuyOrder buyOrder);

    Map<CryptoType, CryptoCurrencyData> getFunds();

    UUID getId();
}
