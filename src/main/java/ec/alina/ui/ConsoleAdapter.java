package ec.alina.ui;

import ec.alina.domain.config.BootAdapter;
import ec.alina.domain.use_cases.UserLoginUseCase;
import ec.alina.domain.use_cases.UserLogoutUseCase;
import ec.alina.domain.use_cases.UserRegistrationUseCase;
import ec.alina.ui.menu.MainMenu;
import ec.alina.ui.menu.Menu;

import java.util.Scanner;

import static java.lang.System.out;

public class ConsoleAdapter implements BootAdapter {
    private final Scanner scanner;

    private Menu mainMenu;
    private Menu currentMenu;

    public ConsoleAdapter(
            UserRegistrationUseCase userRegistrationUseCase,
            UserLoginUseCase userLoginUseCase,
            UserLogoutUseCase userLogoutUseCase
    ) {
        this.scanner = new Scanner(System.in);
        this.mainMenu = new MainMenu(userRegistrationUseCase, userLoginUseCase, scanner).build();
        this.currentMenu = mainMenu;
    }

    @Override
    public void boot() {
        System.out.println("Welcome to the Crypto Exchange System");
        while (true) {
            try {
                currentMenu.show();
                var itemNumber = Integer.parseInt(scanner.nextLine());
                var executed = currentMenu.selectItem(itemNumber);
                if (!executed) {
                    out.println("Please, enter a valid option");
                }
            } catch (NumberFormatException ex) {
                out.println("Please, enter a valid number");
            }
        }
    }
}
