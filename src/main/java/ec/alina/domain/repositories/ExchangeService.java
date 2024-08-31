package ec.alina.domain.repositories;

import ec.alina.domain.models.BuyOrder;
import ec.alina.domain.models.SellOrder;

public interface ExchangeService {

    void placeSellOrder(SellOrder sellOrder, OrderFulfilledCallback callback);

    void placeBuyOrder(BuyOrder buyOrder, OrderFulfilledCallback callback);
}
