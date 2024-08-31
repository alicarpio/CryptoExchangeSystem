package ec.alina.domain.use_cases;

import ec.alina.domain.models.Wallet;
import ec.alina.domain.repositories.WalletRepository;
import ec.alina.domain.validations.exceptions.IllegalAmount;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class DepositMoneyUseCase {
    private final WalletRepository wallets;

    public DepositMoneyUseCase(WalletRepository wallets) {
        this.wallets = wallets;
    }

    public Boolean invoke(UUID userId, BigDecimal amount) throws IllegalAmount {
        Wallet wallet = wallets.findWalletByUserId(userId);
        Objects.requireNonNull(wallet, "Wallet not found");

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalAmount();
        }

        wallet.setFiatBalance(wallet.getFiatBalance().add(amount));

        return true;
    }
}
