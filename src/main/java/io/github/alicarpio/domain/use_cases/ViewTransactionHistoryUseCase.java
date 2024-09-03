package io.github.alicarpio.domain.use_cases;

import io.github.alicarpio.domain.models.Transaction;
import io.github.alicarpio.domain.repositories.TransactionRepository;

import java.util.List;
import java.util.UUID;

public class ViewTransactionHistoryUseCase {
    private final TransactionRepository transactions;

    public ViewTransactionHistoryUseCase(TransactionRepository transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> invoke(UUID userId) {
        return transactions.getTransactionsByUserId(userId);
    }
}
