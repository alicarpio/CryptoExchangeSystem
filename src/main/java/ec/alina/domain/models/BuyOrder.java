package ec.alina.domain.models;

import ec.alina.domain.enums.CrytoType;

import java.math.BigDecimal;
import java.util.UUID;

public class BuyOrder {
    private CrytoType cryptoCurrency;
    private BigDecimal amount;
    private BigDecimal maximumPrice;
    private UUID userId;

    public BuyOrder(CrytoType cryptoCurrency, BigDecimal amount, BigDecimal maximumPrice, UUID userId) {
        this.cryptoCurrency = cryptoCurrency;
        this.amount = amount;
        this.maximumPrice = maximumPrice;
        this.userId = userId;
    }

    public CrytoType getCryptoCurrency() {
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
