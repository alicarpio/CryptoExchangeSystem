package io.github.alicarpio.domain.models;

import io.github.alicarpio.domain.enums.CryptoType;

import java.math.BigDecimal;
import java.util.UUID;

public class SellOrder {
    private final CryptoType cryptoCurrency;
    private final BigDecimal amount;
    private final BigDecimal minimumPrice;
    private final UUID userId;

    public SellOrder(CryptoType cryptoCurrency, BigDecimal amount, BigDecimal minimumPrice, UUID userId) {
        this.cryptoCurrency = cryptoCurrency;
        this.amount = amount;
        this.minimumPrice = minimumPrice;
        this.userId = userId;
    }

    public CryptoType getCryptoCurrency() {
        return cryptoCurrency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getMinimumPrice() {
        return minimumPrice;
    }

    public UUID getUserId() {
        return userId;
    }
}
