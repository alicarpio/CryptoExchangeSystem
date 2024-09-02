package ec.alina.ui.menu;

import ec.alina.domain.enums.CryptoType;
import ec.alina.domain.enums.TransactionType;
import ec.alina.domain.models.Transaction;
import ec.alina.domain.models.User;
import ec.alina.domain.models.Wallet;
import ec.alina.domain.use_cases.*;
import ec.alina.domain.validations.InputValidator;
import ec.alina.domain.validations.exceptions.IllegalAmountException;
import ec.alina.domain.validations.exceptions.ValidationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;


public class ExchangeMenu extends Menu {
    private final UserLogoutUseCase userLogoutUseCase;
    private final ViewWalletBalanceUseCase viewWalletBalanceUseCase;
    private final GetCurrentUseCase getCurrentUseCase;
    private final DepositMoneyUseCase depositMoneyUseCase;
    private final ViewTransactionHistoryUseCase viewTransactionHistoryUseCase;
    private final BuyCryptoDirectlyUseCase buyCryptoDirectlyUseCase;
    private final Scanner scanner;
    private final MenuNavigator navigator;

    public ExchangeMenu(
            UserLogoutUseCase userLogoutUseCase,
            ViewWalletBalanceUseCase viewWalletBalanceUseCase,
            GetCurrentUseCase getCurrentUseCase,
            DepositMoneyUseCase depositMoneyUseCase,
            ViewTransactionHistoryUseCase viewTransactionHistoryUseCase,
            BuyCryptoDirectlyUseCase buyCryptoDirectlyUseCase,
            Scanner scanner,
            MenuNavigator navigator) {
        super("Crypto Exchange Menu");
        this.userLogoutUseCase = userLogoutUseCase;
        this.viewWalletBalanceUseCase = viewWalletBalanceUseCase;
        this.getCurrentUseCase = getCurrentUseCase;
        this.depositMoneyUseCase = depositMoneyUseCase;
        this.viewTransactionHistoryUseCase = viewTransactionHistoryUseCase;
        this.buyCryptoDirectlyUseCase = buyCryptoDirectlyUseCase;
        this.scanner = scanner;
        this.navigator = navigator;
    }

    @Override
    public Menu build() {
        addItem(new MenuItem(1, "Deposit money", this::onDeposit));
        addItem(new MenuItem(2, "Direct crypto purchase", this::onDirectCryptoPurchase));
        addItem(new MenuItem(3, "View wallet balance", this::onViewWalletBalance));
        addItem(new MenuItem(4, "View transaction history", this::onViewTransactionHistory));
        addItem(new LogoutMenuItem(5, userLogoutUseCase, navigator));
        return this;
    }

    private void onViewWalletBalance() {
        int index = 1;
        out.println("---------------- Wallet balance -----------------");
        User currentUser = getCurrentUseCase.invoke();
        Wallet wallet = viewWalletBalanceUseCase.invoke(currentUser.getId());
        BigDecimal currentBalance = wallet.getFiatBalance();
        ConcurrentMap<CryptoType, BigDecimal> currentCryptoHoldings = wallet.getCrytoHoldings();
        out.println("Fiat balance: " + "$" + currentBalance);
        out.println("----------------- Crypto holdings ----------------- ");
        for (CryptoType cryptoType : currentCryptoHoldings.keySet()) {
            out.println(index + ". " + cryptoType + ": " + currentCryptoHoldings.get(cryptoType));
            index++;
        }
        out.println(" ");
    }

    private void onDeposit() {
        out.println("Enter the amount you want to deposit: ");
        String amount = scanner.nextLine();

        if (!InputValidator.isValidNumber(amount)) {
            out.println("Invalid amount entered. Please enter a valid number.");
            return;
        }

        BigDecimal depositAmount = new BigDecimal(amount);

        try {
            depositMoneyUseCase.invoke(getCurrentUseCase.invoke().getId(), depositAmount);
            out.println("Deposit successful");
        } catch (IllegalAmountException ex) {
            out.println("Something went wrong!");
            out.println(ex.getMessage());
        }
    }

    private void onViewTransactionHistory() {
        AtomicInteger counter = new AtomicInteger(1);
        out.println("--------------- Transaction history ----------------");
        List<Transaction> userTransactions = viewTransactionHistoryUseCase.invoke(getCurrentUseCase.invoke().getId());
        userTransactions.forEach(transaction -> {
            String transactionNumber = String.format("TC%02d", counter.getAndIncrement());
            String amountSign = transaction.getTransactionType() == TransactionType.BUY ? "-" : "+";
            String formattedPrice = amountSign + "$" + transaction.getPrice().toString();
            String amount = transaction.getAmount().toString();

            out.println("---------------- "+transactionNumber+ " ----------------");
            out.println("Transaction type: " + transaction.getTransactionType());
            out.println("Crypto currency: " + amount + " "+  transaction.getCryptoCurrency());
            out.println("Price: " + formattedPrice);
        });
    }

    private void onDirectCryptoPurchase() {
        out.println("Enter the crypto currency you want to buy: ");
        String cryptoCurrency = scanner.nextLine();
        out.println("Enter the amount you want to buy: ");
        String amount = scanner.nextLine();


        if (!InputValidator.isValidNumber(amount)) {
            out.println("Invalid amount entered. Please enter a valid number.");
            return;
        }

        if (!InputValidator.isValidCrypto(cryptoCurrency.toUpperCase())) {
            out.println("Invalid crypto currency entered. Please enter a valid crypto currency.");
            return;
        }

        CryptoType cryptoType = CryptoType.valueOf(cryptoCurrency.toUpperCase());

        BigDecimal cryptoAmount = new BigDecimal(amount);

        try {
            buyCryptoDirectlyUseCase.invoke(cryptoType, cryptoAmount);
            out.println("Purchase successful");
        } catch (ValidationException ex) {
            out.println("Something went wrong!");
            out.println(ex.getMessage());
        }
    }
}
