package ec.alina.domain.repositories;

import ec.alina.domain.models.Wallet;

import java.util.UUID;

public interface WalletRepository {

    void save(Wallet wallet);

    Wallet findWalletByUserId(UUID userId);
}
