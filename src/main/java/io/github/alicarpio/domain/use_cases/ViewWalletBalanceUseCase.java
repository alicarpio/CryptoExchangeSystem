package io.github.alicarpio.domain.use_cases;

import io.github.alicarpio.domain.models.Wallet;
import io.github.alicarpio.domain.repositories.WalletRepository;

import java.util.UUID;

public class ViewWalletBalanceUseCase {
    private final WalletRepository wallets;

    public ViewWalletBalanceUseCase(WalletRepository wallets) {
        this.wallets = wallets;
    }

    public Wallet invoke(UUID userId) {
        return wallets.findWalletByUserId(userId);
    }
}
