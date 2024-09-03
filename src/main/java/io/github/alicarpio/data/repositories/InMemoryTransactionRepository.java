package io.github.alicarpio.data.repositories;

import io.github.alicarpio.domain.models.Transaction;
import io.github.alicarpio.domain.repositories.TransactionRepository;

import java.util.*;

public class InMemoryTransactionRepository implements TransactionRepository {
    private final Map<UUID, List<Transaction>> transactions;

    public InMemoryTransactionRepository() {
        this.transactions = new HashMap<>();
    }

    @Override
    public void save(UUID userId,Transaction transaction) {
        transactions.putIfAbsent(userId, new ArrayList<>());
        transactions.get(userId).add(transaction);
    }

    @Override
    public List<Transaction> getTransactionsByUserId(UUID userId) {
        return transactions.getOrDefault(userId, Collections.emptyList());    }
}
