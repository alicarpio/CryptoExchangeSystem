package ec.alina.domain.models;

import ec.alina.Utils;
import ec.alina.domain.enums.CryptoType;

import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class Exchange {
    private final UUID id;
    private final Map<CryptoType,CryptoCurrencyData> initialFunds;
    private final List<BuyOrder> buyOrders;
    private final List<SellOrder> sellOrders;

    public Exchange(Map<CryptoType,CryptoCurrencyData> initialFunds) {
        id = Utils.generateUniqueId();
        this.initialFunds = new ConcurrentHashMap<>(initialFunds);
        buyOrders = new ArrayList<>();
        sellOrders = new ArrayList<>();
    }

    public Map<CryptoType, CryptoCurrencyData> getInitialFunds() {
        return initialFunds;
    }

    public UUID getId() {
        return id;
    }

    public List<BuyOrder> getBuyOrders() {
        return buyOrders;
    }

    public List<SellOrder> getSellOrders() {
        return sellOrders;
    }
}
