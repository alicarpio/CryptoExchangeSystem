package ec.alina.domain.use_cases;

import ec.alina.domain.models.Wallet;
import ec.alina.domain.repositories.WalletRepository;

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
