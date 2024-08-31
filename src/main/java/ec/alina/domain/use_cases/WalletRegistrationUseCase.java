package ec.alina.domain.use_cases;

import ec.alina.domain.models.Wallet;
import ec.alina.domain.repositories.WalletRepository;

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
