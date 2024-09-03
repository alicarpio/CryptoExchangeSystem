package ec.alina.domain.models;

import ec.alina.domain.enums.CryptoType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Wallet {
    private final UUID userId;
    private BigDecimal fiatBalance;
    private final ConcurrentMap<CryptoType, BigDecimal> crytoHoldings;

    public Wallet(UUID userId) {
        this.userId = userId;
        this.fiatBalance = new BigDecimal(BigInteger.ZERO);
        this.crytoHoldings = new ConcurrentHashMap<>();
    }

    public UUID getUserId() {
        return userId;
    }

    public BigDecimal getFiatBalance() {
        return fiatBalance;
    }

    public void setFiatBalance(BigDecimal fiatBalance) {
        this.fiatBalance = fiatBalance;
    }

    public ConcurrentMap<CryptoType, BigDecimal> getCrytoHoldings() {
        return crytoHoldings;
    }
}
