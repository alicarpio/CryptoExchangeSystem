package ec.alina.domain.models;

import ec.alina.domain.enums.CryptoType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Exchange {
    private final Map<CryptoType,CryptoCurrencyData> initialFunds;
    private final BlockingDeque<OrderTask> orders;

    public Exchange(Map<CryptoType,CryptoCurrencyData> initialFunds) {
        this.initialFunds = new HashMap<>(initialFunds);
        orders = new LinkedBlockingDeque<>();
    }

    public Map<CryptoType, CryptoCurrencyData> getInitialFunds() {
        return initialFunds;
    }

    public BlockingDeque<OrderTask> getOrders() {
        return orders;
    }
}
