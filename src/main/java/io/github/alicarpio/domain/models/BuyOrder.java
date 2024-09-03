package io.github.alicarpio.domain.models;

import io.github.alicarpio.domain.enums.CryptoType;

import java.math.BigDecimal;
import java.util.UUID;

public class BuyOrder {
    private final CryptoType cryptoCurrency;
    private final BigDecimal amount;
    private final BigDecimal maximumPrice;
    private final UUID userId;

    public BuyOrder(CryptoType cryptoCurrency, BigDecimal amount, BigDecimal maximumPrice, UUID userId) {
        this.cryptoCurrency = cryptoCurrency;
        this.amount = amount;
        this.maximumPrice = maximumPrice;
        this.userId = userId;
    }

    public CryptoType getCryptoCurrency() {
        return cryptoCurrency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getMaximumPrice() {
        return maximumPrice;
    }

    public UUID getUserId() {
        return userId;
    }
}
