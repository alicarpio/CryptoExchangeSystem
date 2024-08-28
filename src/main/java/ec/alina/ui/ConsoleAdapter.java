package ec.alina.ui;

import ec.alina.domain.config.BootAdapter;
import ec.alina.domain.use_cases.UserLoginUseCase;
import ec.alina.domain.use_cases.UserLogoutUseCase;
import ec.alina.domain.use_cases.UserRegistrationUseCase;
import ec.alina.domain.validations.exceptions.ValidationException;

import java.util.Scanner;

public class ConsoleAdapter implements BootAdapter {
    private final Scanner scanner;
    private final UserLoginUseCase userLoginUseCase;
    private final UserLogoutUseCase userLogoutUseCase;
    private final UserRegistrationUseCase userRegistrationUseCase;

    public ConsoleAdapter(UserRegistrationUseCase userRegistrationUseCase, UserLoginUseCase userLoginUseCase, UserLogoutUseCase userLogoutUseCase) {
        this.scanner = new Scanner(System.in);
        this.userLoginUseCase = userLoginUseCase;
        this.userLogoutUseCase = userLogoutUseCase;
        this.userRegistrationUseCase = userRegistrationUseCase;
    }

    @Override
    public void boot() {
        System.out.println("Welcome to the Crypto Exchange System");
        printMenu();

        while (scanner.hasNextInt()) {
            int selectedOption = Integer.parseInt(scanner.nextLine());
            dispatchUseCase(selectedOption);
            printMenu();
        }
    }

    private void printMenu() {
        System.out.println("Choose one option to continue:");
        System.out.println("1.Register");
        System.out.println("2.Log in");
        System.out.println("3.Log out");
    }

    private void dispatchUseCase(int option) {
        switch (option) {
            case 1:
                dispatchUserRegistrationUseCase();
                break;
            case 2:
                dispatchUserLogInUsecase();
                break;
            case 3:
                dispatchUserLogOutUseCase();
                break;
        }
    }

    private void dispatchUserLogOutUseCase() {
    }

    private void dispatchUserLogInUsecase() {

    }

    private void dispatchUserRegistrationUseCase() {
        System.out.println("Enter your name: ");
        String userName = scanner.nextLine();
        System.out.println("Enter your email: ");
        String userEmail = scanner.nextLine();
        System.out.println("Enter your password: ");
        String userPassword = scanner.nextLine();

        try {
            userRegistrationUseCase.invoke(userName, userEmail, userPassword);
            System.out.println("User registration successful");
        } catch (ValidationException ex) {
            System.out.println(ex.getMessage());
            System.out.println("The user could not be saved");
        }
    }
}
