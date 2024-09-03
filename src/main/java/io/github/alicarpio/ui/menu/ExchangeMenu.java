package io.github.alicarpio.ui.menu;

import io.github.alicarpio.CryptoExchangeSystem;
import io.github.alicarpio.domain.enums.CryptoType;
import io.github.alicarpio.domain.enums.TransactionType;
import io.github.alicarpio.domain.models.Transaction;
import io.github.alicarpio.domain.models.User;
import io.github.alicarpio.domain.models.Wallet;
import io.github.alicarpio.domain.use_cases.*;
import io.github.alicarpio.domain.validations.InputValidator;
import io.github.alicarpio.domain.validations.exceptions.IllegalAmountException;
import io.github.alicarpio.domain.validations.exceptions.ValidationException;

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
    private final PlaceBuyOrderUseCase placeBuyOrderUseCase;
    private final PlaceSellOrderUseCase placeSellOrderUseCase;
    private final Scanner scanner;
    private final MenuNavigator navigator;

    public ExchangeMenu(
            UserLogoutUseCase userLogoutUseCase,
            ViewWalletBalanceUseCase viewWalletBalanceUseCase,
            GetCurrentUseCase getCurrentUseCase,
            DepositMoneyUseCase depositMoneyUseCase,
            ViewTransactionHistoryUseCase viewTransactionHistoryUseCase,
            BuyCryptoDirectlyUseCase buyCryptoDirectlyUseCase,
            PlaceBuyOrderUseCase placeBuyOrderUseCase,
            PlaceSellOrderUseCase placeSellOrderUseCase,
            Scanner scanner,
            MenuNavigator navigator) {
        super("Crypto Exchange Menu");
        this.userLogoutUseCase = userLogoutUseCase;
        this.viewWalletBalanceUseCase = viewWalletBalanceUseCase;
        this.getCurrentUseCase = getCurrentUseCase;
        this.depositMoneyUseCase = depositMoneyUseCase;
        this.viewTransactionHistoryUseCase = viewTransactionHistoryUseCase;
        this.buyCryptoDirectlyUseCase = buyCryptoDirectlyUseCase;
        this.placeBuyOrderUseCase = placeBuyOrderUseCase;
        this.placeSellOrderUseCase = placeSellOrderUseCase;
        this.scanner = scanner;
        this.navigator = navigator;
    }

    @Override
    public Menu build() {
        addItem(new MenuItem(1, "Deposit money", this::onDeposit));
        addItem(new MenuItem(2, "Direct crypto purchase", this::onDirectCryptoPurchase));
        addItem(new MenuItem(3, "Place buy Order", this::onPlaceBuyOrder));
        addItem(new MenuItem(4, "Place sell Order", this::onPlaceSellOrder));
        addItem(new MenuItem(5, "View wallet balance", this::onViewWalletBalance));
        addItem(new MenuItem(6, "View transaction history", this::onViewTransactionHistory));
        addItem(new LogoutMenuItem(7, userLogoutUseCase, navigator));
        addItem(new ExitMenuItem(8));
        return this;
    }

    private void onViewWalletBalance() {
        int index = 1;
        out.println("\u001b[34m ----------------- Wallet balance ------------------ \u001b[0m");
        User currentUser = getCurrentUseCase.invoke();
        Wallet wallet = viewWalletBalanceUseCase.invoke(currentUser.getId());
        BigDecimal currentBalance = wallet.getFiatBalance();
        ConcurrentMap<CryptoType, BigDecimal> currentCryptoHoldings = wallet.getCrytoHoldings();
        out.println("Fiat balance: " + "$" + currentBalance);
        out.println("\u001b[34m ----------------- Crypto holdings ----------------- \u001b[0m");
        for (CryptoType cryptoType : currentCryptoHoldings.keySet()) {
            out.printf("%d. %s: %s%n", index, cryptoType, currentCryptoHoldings.get(cryptoType));
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
            out.println("\u001b[46m    Deposit successful    \u001b[0m");
        } catch (IllegalAmountException ex) {
            out.println("\u001b[41m    Something went wrong!    \u001b[0m");
            out.println("\u001b[31m"+ex.getMessage()+"\u001b[0m");
        }
    }

    private void onViewTransactionHistory() {
        AtomicInteger counter = new AtomicInteger(1);
        out.println("\u001b[34m ---------------- Transaction history ----------------- \u001b[0m");
        List<Transaction> userTransactions = viewTransactionHistoryUseCase.invoke(getCurrentUseCase.invoke().getId());
        userTransactions.forEach(transaction -> {
            String transactionNumber = String.format("TC%02d", counter.getAndIncrement());
            String amountSign = transaction.getTransactionType() == TransactionType.BUY ? "-" : "+";
            String formattedPrice = amountSign + "$" + transaction.getPrice().toString();
            String amount = transaction.getAmount().toString();

            out.println("-------------------------- "+transactionNumber+ " --------------------------");
            out.println("Transaction type: " + transaction.getTransactionType());
            out.println("Crypto currency: " + amount + " "+  transaction.getCryptoCurrency());
            out.println("Price: " + formattedPrice);
            out.println(" ");
        });
    }

    private void onDirectCryptoPurchase() {
        out.println("\u001b[34m -------------------- Current market prices -------------------- \u001b[0m");
        out.println("- BTC: $" + CryptoExchangeSystem.getExchange().getPriceFor(CryptoType.BTC));
        out.println("- ETH: $" + CryptoExchangeSystem.getExchange().getPriceFor(CryptoType.ETH));
        out.println("- LTC: $" + CryptoExchangeSystem.getExchange().getPriceFor(CryptoType.LTC));
        out.println(" ");

        out.println("Enter the crypto currency you want to buy: ");
        String cryptoCurrency = scanner.nextLine();
        out.println("Enter the amount you want to buy: ");
        String amount = scanner.nextLine();

        if (!InputValidator.isValidCrypto(cryptoCurrency.toUpperCase())) {
            out.println("\u001b[41m Invalid crypto currency entered. Please enter a valid crypto currency. \u001b[0m");
            return;
        }

        CryptoType cryptoType = CryptoType.valueOf(cryptoCurrency.toUpperCase());

        if (!InputValidator.isValidNumber(amount)) {
            out.println("\u001b[41m Invalid amount entered. Please enter a valid number. \u001b[0m");
            return;
        }

        BigDecimal cryptoAmount = new BigDecimal(amount);

        try {
            buyCryptoDirectlyUseCase.invoke(cryptoType, cryptoAmount);
            out.println("\u001b[46m    Purchase successful    \u001b[0m");
        } catch (ValidationException ex) {
            out.println("\u001b[41m    Something went wrong!    \u001b[0m");
            out.println("\u001b[31m"+ex.getMessage()+"\u001b[0m");
        }
    }

    private void onPlaceBuyOrder() {
        out.println("Enter the type of crypto currency you want to buy: ");
        String cryptoCurrency = scanner.nextLine();

        if (!InputValidator.isValidCrypto(cryptoCurrency.toUpperCase())) {
            out.println("\u001b[41m Invalid crypto currency entered. Please enter a valid crypto currency. \u001b[0m");
            return;
        }

        CryptoType cryptoType = CryptoType.valueOf(cryptoCurrency.toUpperCase());
        var currentMarketPrice = CryptoExchangeSystem.getExchange().getPriceFor(cryptoType);
        out.println("\u001b[45m Current market price for "+cryptoType+" is: $"+currentMarketPrice +" \u001b[0m");

        out.println("Enter the amount you want to buy: ");
        String amount = scanner.nextLine();
        out.println("Enter the maximum price you are willing to pay: ");
        String price = scanner.nextLine();


        if (!InputValidator.isValidNumber(amount) || !InputValidator.isValidNumber(price)) {
            out.println("\u001b[41m Invalid amount entered. Please enter a valid number. \u001b[0m");
            return;
        }

        try{
            placeBuyOrderUseCase.invoke(cryptoType, new BigDecimal(amount), new BigDecimal(price));
            out.println("\u001b[42m    Buy order placed successfully     \u001b[0m");
        }catch (ValidationException ex) {
            out.println("\u001b[41m    Something went wrong!    \u001b[0m");
            out.println("\u001b[31m"+ex.getMessage()+"\u001b[0m");
        }
    }

    private void onPlaceSellOrder() {
        out.println("Enter the type of crypto currency you want to sell: ");
        String cryptoCurrency = scanner.nextLine();

        if (!InputValidator.isValidCrypto(cryptoCurrency.toUpperCase())) {
            out.println("\u001b[41m Invalid crypto currency entered. Please enter a valid crypto currency. \u001b[0m");
            return;
        }

        CryptoType cryptoType = CryptoType.valueOf(cryptoCurrency.toUpperCase());
        var currentMarketPrice = CryptoExchangeSystem.getExchange().getPriceFor(cryptoType);
        out.println("\u001b[45m Current market price for "+cryptoType+" is: $"+currentMarketPrice +" \u001b[0m");

        out.println("Enter the amount you want to sell: ");
        String amount = scanner.nextLine();
        out.println("Enter the minimum price you are willing to accept: ");
        String price = scanner.nextLine();

        if (!InputValidator.isValidNumber(amount) || !InputValidator.isValidNumber(price)) {
            out.println("\u001b[41m Invalid amount entered. Please enter a valid number. \u001b[0m");
            return;
        }

        try{
            placeSellOrderUseCase.invoke(cryptoType, new BigDecimal(amount), new BigDecimal(price));
            out.println("\u001b[42m    Sell order placed successfully    \u001b[0m");
        }catch (ValidationException ex) {
            out.println("\u001b[41m    Something went wrong!    \u001b[0m");
            out.println("\u001b[31m"+ex.getMessage()+"\u001b[0m");
        }
    }
}
