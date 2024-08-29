package ec.alina.ui.menu;

import ec.alina.domain.use_cases.UserLoginUseCase;
import ec.alina.domain.use_cases.UserRegistrationUseCase;
import ec.alina.domain.validations.exceptions.InvalidEmailOrPasswordException;
import ec.alina.domain.validations.exceptions.ValidationException;

import java.util.Scanner;

import static java.lang.System.out;

public class MainMenu extends Menu {
    private final UserRegistrationUseCase userRegistrationUseCase;
    private final UserLoginUseCase userLoginUseCase;
    private final Scanner scanner;
    private final MenuNavigator navigator;

    public MainMenu(UserRegistrationUseCase userRegistrationUseCase, UserLoginUseCase userLoginUseCase, Scanner scanner, MenuNavigator navigator) {
        super("Main menu");
        this.userRegistrationUseCase = userRegistrationUseCase;
        this.userLoginUseCase = userLoginUseCase;
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
        out.println("----------- Log in -----------");
        out.println("Enter your email: ");
        String userEmail = scanner.nextLine();
        out.println("Enter your password: ");
        String userPassword = scanner.nextLine();

        try {
            userLoginUseCase.invoke(userEmail, userPassword);
            out.println("Log in successful");
            navigator.navigateTo(MenuNavigator.EXCHANGE_MENU);
        } catch (InvalidEmailOrPasswordException ex) {
            out.println(ex.getMessage());
            out.println("Something went wrong!");
        }
    }

    private void onRegisterUser() {
        out.println("----------- Registration -----------");
        out.println("Enter your name: ");
        String userName = scanner.nextLine();
        out.println("Enter your email: ");
        String userEmail = scanner.nextLine();
        out.println("Enter your password: ");
        String userPassword = scanner.nextLine();

        try {
            userRegistrationUseCase.invoke(userName, userEmail, userPassword);
            out.println("User registration successful");
        } catch (ValidationException ex) {
            out.println(ex.getMessage());
            out.println("The user could not be saved");
        }
    }
}
