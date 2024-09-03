package io.github.alicarpio.domain.use_cases;

import io.github.alicarpio.domain.models.Wallet;
import io.github.alicarpio.domain.repositories.WalletRepository;
import io.github.alicarpio.domain.validations.NumberValidator;
import io.github.alicarpio.domain.validations.exceptions.IllegalAmountException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class DepositMoneyUseCase {
    private final WalletRepository wallets;
    private final NumberValidator numberValidator = new NumberValidator();

    public DepositMoneyUseCase(WalletRepository wallets) {
        this.wallets = wallets;
    }

    public Boolean invoke(UUID userId, BigDecimal amount) throws IllegalAmountException {
        Wallet wallet = wallets.findWalletByUserId(userId);
        Objects.requireNonNull(wallet, "Wallet not found");

        numberValidator.validate(amount, "Deposit amount");

        wallet.setFiatBalance(wallet.getFiatBalance().add(amount));

        return true;
    }
}
