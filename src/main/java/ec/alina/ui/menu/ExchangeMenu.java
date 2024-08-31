package ec.alina.ui.menu;

import ec.alina.domain.enums.CrytoType;
import ec.alina.domain.models.User;
import ec.alina.domain.models.Wallet;
import ec.alina.domain.use_cases.DepositMoneyUseCase;
import ec.alina.domain.use_cases.GetCurrentUseCase;
import ec.alina.domain.use_cases.UserLogoutUseCase;
import ec.alina.domain.use_cases.ViewWalletBalanceUseCase;
import ec.alina.domain.validations.NumberValidator;
import ec.alina.domain.validations.exceptions.IllegalAmountException;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.concurrent.ConcurrentMap;

import static java.lang.System.out;


public class ExchangeMenu extends Menu {
    private final UserLogoutUseCase userLogoutUseCase;
    private final ViewWalletBalanceUseCase viewWalletBalanceUseCase;
    private final GetCurrentUseCase getCurrentUseCase;
    private final DepositMoneyUseCase depositMoneyUseCase;
    private final Scanner scanner;
    private final MenuNavigator navigator;

    private final NumberValidator numberValidator = new NumberValidator();

    public ExchangeMenu(
            UserLogoutUseCase userLogoutUseCase,
            ViewWalletBalanceUseCase viewWalletBalanceUseCase,
            GetCurrentUseCase getCurrentUseCase,
            DepositMoneyUseCase depositMoneyUseCase,
            Scanner scanner,
            MenuNavigator navigator) {
        super("Crypto Exchange Menu");
        this.userLogoutUseCase = userLogoutUseCase;
        this.viewWalletBalanceUseCase = viewWalletBalanceUseCase;
        this.getCurrentUseCase = getCurrentUseCase;
        this.depositMoneyUseCase = depositMoneyUseCase;
        this.scanner = scanner;
        this.navigator = navigator;
    }

    @Override
    public Menu build() {
        addItem(new MenuItem(1, "View wallet balance", this::onViewWalletBalance));
        addItem(new MenuItem(2, "Deposit money", this::onDeposit));
        addItem(new LogoutMenuItem(3, userLogoutUseCase, navigator));
        return this;
    }

    private void onViewWalletBalance() {
        int index = 1;
        out.println("---------------- Wallet balance -----------------");
        User currentUser = getCurrentUseCase.invoke();
        Wallet wallet = viewWalletBalanceUseCase.invoke(currentUser.getId());
        BigDecimal currentBalance = wallet.getFiatBalance();
        ConcurrentMap<CrytoType, BigDecimal> currentCryptoHoldings = wallet.getCrytoHoldings();
        out.println("Fiat balance: " + "$" + currentBalance);
        out.println("----------------- Crypto holdings ----------------- ");
        for (CrytoType cryptoType : currentCryptoHoldings.keySet()) {
            out.println(index + ". " + cryptoType + ": " + currentCryptoHoldings.get(cryptoType));
            index++;
        }
        out.println(" ");
    }

    private void onDeposit() {
        out.println("Enter the amount you want to deposit: ");
        String amount = scanner.nextLine();
        BigDecimal depositAmount = new BigDecimal(amount);

        if (!NumberValidator.isValidNumber(amount)) {
            out.println("Invalid amount entered. Please enter a valid amount.");
        }

        try {
            numberValidator.validate(depositAmount, "Deposit amount");
            depositMoneyUseCase.invoke(getCurrentUseCase.invoke().getId(), depositAmount);
            out.println("Deposit successful");

        } catch (IllegalAmountException ex) {
            out.println("Something went wrong!");
            out.println(ex.getMessage());
        }   catch (NumberFormatException ex) {
            out.println("Invalid number format");
        }
    }
}
