package ec.alina.data.repositories;

import ec.alina.domain.models.Transaction;
import ec.alina.domain.repositories.TransactionRepository;

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
