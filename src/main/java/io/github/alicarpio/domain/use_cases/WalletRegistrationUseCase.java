package io.github.alicarpio.domain.use_cases;

import io.github.alicarpio.domain.models.Wallet;
import io.github.alicarpio.domain.repositories.WalletRepository;

import java.util.UUID;

public class WalletRegistrationUseCase {
    private final WalletRepository wallets;

    public WalletRegistrationUseCase(WalletRepository wallets) {
        this.wallets = wallets;
    }

    public void invoke(UUID userId) {
        Wallet wallet = new Wallet(userId);
        wallets.save(wallet);
    }
}
