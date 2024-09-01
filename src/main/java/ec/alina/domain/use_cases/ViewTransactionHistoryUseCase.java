package ec.alina.domain.use_cases;

import ec.alina.domain.models.Transaction;
import ec.alina.domain.repositories.TransactionRepository;

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
