package io.github.alicarpio.data.repositories;

import io.github.alicarpio.domain.models.Wallet;
import io.github.alicarpio.domain.repositories.WalletRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryWalletRepository implements WalletRepository {
    private final Map<UUID, Wallet> wallets = new HashMap<>();

    @Override
    public void save(Wallet wallet) {
        wallets.put(wallet.getUserId(), wallet);
    }

    @Override
    public Wallet findWalletByUserId(UUID userId) {
        return wallets.get(userId);
    }
}
