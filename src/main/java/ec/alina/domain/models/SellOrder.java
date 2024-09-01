package ec.alina.domain.models;

import ec.alina.domain.enums.CrytoType;

import java.math.BigDecimal;
import java.util.UUID;

public class SellOrder {
    private CrytoType cryptoCurrency;
    private BigDecimal amount;
    private BigDecimal minimumPrice;
    private UUID userId;

    public SellOrder(CrytoType cryptoCurrency, BigDecimal amount, BigDecimal minimumPrice, UUID userId) {
        this.cryptoCurrency = cryptoCurrency;
        this.amount = amount;
        this.minimumPrice = minimumPrice;
        this.userId = userId;
    }

    public CrytoType getCryptoCurrency() {
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
