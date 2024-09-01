package ec.alina.domain.models;

import ec.alina.domain.enums.CrytoType;
import ec.alina.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

public class Transaction {
    private final UUID id = UUID.randomUUID();
    private final CrytoType cryptoCurrency;
    private final BigDecimal amount;
    private final BigDecimal price;
    private final TransactionType transactionType;
    private final UUID sellerId;
    private final UUID buyerId;

    public Transaction(CrytoType cryptoCurrency, BigDecimal amount, BigDecimal price, TransactionType transactionType, UUID sellerId, UUID buyerId) {
        this.cryptoCurrency = cryptoCurrency;
        this.amount = amount;
        this.price = price;
        this.transactionType = transactionType;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
    }

    public CrytoType getCryptoCurrency() {
        return cryptoCurrency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public UUID getSellerId() {
        return sellerId;
    }

    public UUID getBuyerId() {
        return buyerId;
    }

    public UUID getId() {
        return id;
    }
}
