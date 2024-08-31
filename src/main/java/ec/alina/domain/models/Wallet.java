package ec.alina.domain.models;

import ec.alina.domain.enums.CrytoType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Wallet {
    private UUID userId;
    private BigDecimal fiatBalance;
    private ConcurrentMap<CrytoType, BigDecimal> crytoHoldings;

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

    public ConcurrentMap<CrytoType, BigDecimal> getCrytoHoldings() {
        return crytoHoldings;
    }

    public void setFiatBalance(BigDecimal fiatBalance) {
        this.fiatBalance = fiatBalance;
    }
}
