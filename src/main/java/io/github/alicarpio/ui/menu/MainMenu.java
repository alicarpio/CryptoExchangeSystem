package io.github.alicarpio.ui.menu;

import io.github.alicarpio.domain.models.User;
import io.github.alicarpio.domain.use_cases.UserLoginUseCase;
import io.github.alicarpio.domain.use_cases.UserRegistrationUseCase;
import io.github.alicarpio.domain.use_cases.WalletRegistrationUseCase;
import io.github.alicarpio.domain.validations.exceptions.InvalidEmailOrPasswordException;
import io.github.alicarpio.domain.validations.exceptions.ValidationException;

import java.util.Scanner;

import static java.lang.System.out;

public class MainMenu extends Menu {
    private final UserRegistrationUseCase userRegistrationUseCase;
    private final UserLoginUseCase userLoginUseCase;
    private final WalletRegistrationUseCase walletRegistrationUseCase;
    private final Scanner scanner;
    private final MenuNavigator navigator;

    public MainMenu(
            UserRegistrationUseCase userRegistrationUseCase,
            UserLoginUseCase userLoginUseCase,
            WalletRegistrationUseCase walletRegistrationUseCase,
            Scanner scanner,
            MenuNavigator navigator) {
        super("Main menu");
        this.userRegistrationUseCase = userRegistrationUseCase;
        this.userLoginUseCase = userLoginUseCase;
        this.walletRegistrationUseCase = walletRegistrationUseCase;
        this.scanner = scanner;
        this.navigator = navigator;
    }

    @Override
    public Menu build() {
        addItem(new MenuItem(1, "Register", this::onRegisterUser));
        addItem(new MenuItem(2, "Log in", this::onLogin));
        addItem(new ExitMenuItem(3));
        return this;
    }

    private void onLogin() {
        out.println("\u001b[35m --------------------- Log in --------------------- \u001b[0m");
        out.println("Enter your email: ");
        String userEmail = scanner.nextLine();
        out.println("Enter your password: ");
        String userPassword = scanner.nextLine();

        try {
            userLoginUseCase.invoke(userEmail, userPassword);
            out.println("\u001b[46m  Log in successful  \u001b[0m");
            navigator.navigateTo(MenuNavigator.EXCHANGE_MENU);
        } catch (InvalidEmailOrPasswordException ex) {
            out.println("\u001b[41m    Something went wrong!    \u001b[0m");
            out.println("\u001b[31m"+ex.getMessage()+"\u001b[0m");
        }
    }

    private void onRegisterUser() {
        out.println("\u001b[35m ------------------- Registration ------------------- \u001b[0m");
        out.println("Enter your name: ");
        String userName = scanner.nextLine();
        out.println("Enter your email: ");
        String userEmail = scanner.nextLine();
        out.println("Enter your password: ");
        String userPassword = scanner.nextLine();

        try {
            User newUser = userRegistrationUseCase.invoke(userName, userEmail, userPassword);
            walletRegistrationUseCase.invoke(newUser.getId());
            out.println("\u001b[46m  User registration successful  \u001b[0m");
        } catch (ValidationException ex) {
            out.println("\u001b[41m The user could not be saved! \u001b[0m");
            out.println("\u001b[31m"+ex.getMessage()+"\u001b[0m");

        }
    }
}
