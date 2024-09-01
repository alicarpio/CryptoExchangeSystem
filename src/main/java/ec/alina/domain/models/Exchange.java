package ec.alina.domain.models;

import ec.alina.Utils;
import ec.alina.domain.enums.CryptoType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Exchange {
    private final UUID id;
    private final Map<CryptoType,CryptoCurrencyData> initialFunds;
    private final BlockingDeque<OrderTask> orders;

    public Exchange(Map<CryptoType,CryptoCurrencyData> initialFunds) {
        this.id = Utils.generateUniqueId();
        this.initialFunds = new HashMap<>(initialFunds);
        orders = new LinkedBlockingDeque<>();
    }

    public Map<CryptoType, CryptoCurrencyData> getInitialFunds() {
        return initialFunds;
    }

    public BlockingDeque<OrderTask> getOrders() {
        return orders;
    }

    public UUID getId() {
        return id;
    }
}
