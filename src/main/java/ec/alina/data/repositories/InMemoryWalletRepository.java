package ec.alina.data.repositories;

import ec.alina.domain.models.Wallet;
import ec.alina.domain.repositories.WalletRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryWalletRepository implements WalletRepository {
    private final Map<UUID, Wallet> wallets = new HashMap<>();

    @Override
    public Wallet findWalletByUserId(UUID userId) {
        return wallets.get(userId);
    }

    @Override
    public void save(Wallet wallet) {
        wallets.put(wallet.getUserId(), wallet);
    }
}
