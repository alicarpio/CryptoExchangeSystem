package io.github.alicarpio.domain.repositories;

import io.github.alicarpio.domain.models.Wallet;

import java.util.UUID;

public interface WalletRepository {

    void save(Wallet wallet);

    Wallet findWalletByUserId(UUID userId);
}
