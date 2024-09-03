package io.github.alicarpio.domain.models;

import io.github.alicarpio.Utils;
import io.github.alicarpio.domain.enums.CryptoType;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

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

    public synchronized BigDecimal getPriceFor(CryptoType cryptoType) {
        return initialFunds.get(cryptoType).getPrice();
    }

    public synchronized void setPriceFor(CryptoType cryptoType, BigDecimal price) {
        CryptoCurrencyData cryptoData = new CryptoCurrencyData(price, initialFunds.get(cryptoType).getQuantity());
        initialFunds.put(cryptoType, cryptoData);
    }

    public <T> T withFunds(Function<Map<CryptoType, CryptoCurrencyData>, ? extends T> callback) {
        synchronized (this) {
            return callback.apply(initialFunds);
        }
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
