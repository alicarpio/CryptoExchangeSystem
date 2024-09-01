package ec.alina.domain.repositories;


import ec.alina.domain.models.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository {

    void save(UUID userId,Transaction transaction);

    List<Transaction> getTransactionsByUserId(UUID userId);
}
