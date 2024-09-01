package ec.alina.domain.use_cases;

import ec.alina.domain.models.Wallet;
import ec.alina.domain.repositories.WalletRepository;
import ec.alina.domain.validations.NumberValidator;
import ec.alina.domain.validations.Validator;
import ec.alina.domain.validations.exceptions.IllegalAmountException;

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
