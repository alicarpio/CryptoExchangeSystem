package ec.alina.domain.repositories;

import ec.alina.domain.models.Wallet;

import java.util.UUID;

public interface WalletRepository {

    Wallet findWalletByUserId(UUID userId);

    void save(Wallet wallet);
}
