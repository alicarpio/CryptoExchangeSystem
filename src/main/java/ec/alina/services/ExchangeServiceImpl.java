package ec.alina.services;

import ec.alina.domain.enums.CryptoType;
import ec.alina.domain.models.BuyOrder;
import ec.alina.domain.models.CryptoCurrencyData;
import ec.alina.domain.models.Exchange;
import ec.alina.domain.models.SellOrder;
import ec.alina.domain.repositories.OrderFulfilledCallback;
import ec.alina.domain.services.ExchangeService;

import java.util.Map;
import java.util.UUID;

public class ExchangeServiceImpl implements ExchangeService {
    private final Exchange exchange;

    public ExchangeServiceImpl(Exchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public void placeSellOrder(SellOrder sellOrder, OrderFulfilledCallback callback) {

    }

    @Override
    public void placeBuyOrder(BuyOrder buyOrder, OrderFulfilledCallback callback) {

    }

    @Override
    public Map<CryptoType, CryptoCurrencyData> getFunds() {
        return exchange.getInitialFunds();
    }

    @Override
    public UUID getId() {
        return exchange.getId();
    }

}
