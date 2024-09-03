package io.github.alicarpio.domain.models;

import java.math.BigDecimal;

public class CryptoCurrencyData {
    private final BigDecimal price;
    private  BigDecimal quantity;

    public CryptoCurrencyData(BigDecimal price, BigDecimal quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

}
