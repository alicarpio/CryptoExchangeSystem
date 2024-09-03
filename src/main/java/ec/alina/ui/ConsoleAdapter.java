package ec.alina.ui;

import ec.alina.domain.config.BootAdapter;
import ec.alina.domain.use_cases.*;
import ec.alina.ui.menu.*;

import java.util.Scanner;

import static java.lang.System.out;

public class ConsoleAdapter implements BootAdapter, MenuNavigatorHost {
    private final Scanner scanner;

    private final MenuNavigator navigator;

    private final Menu mainMenu;
    private final Menu exchangeMenu;
    private Menu currentMenu;

    public ConsoleAdapter(
            UserRegistrationUseCase userRegistrationUseCase,
            UserLoginUseCase userLoginUseCase,
            UserLogoutUseCase userLogoutUseCase,
            GetCurrentUseCase getCurrentUseCase,
            WalletRegistrationUseCase walletRegistrationUseCase,
            ViewWalletBalanceUseCase viewWalletBalanceUseCase,
            DepositMoneyUseCase depositMoneyUseCase,
            ViewTransactionHistoryUseCase viewTransactionHistoryUseCase,
            BuyCryptoDirectlyUseCase buyCryptoDirectlyUseCase,
            PlaceBuyOrderUseCase placeBuyOrderUseCase,
            PlaceSellOrderUseCase placeSellOrderUseCase


    ) {
        navigator = new MenuNavigator(this);

        this.scanner = new Scanner(System.in);
        this.mainMenu = new MainMenu
                (
                        userRegistrationUseCase,
                        userLoginUseCase,
                        walletRegistrationUseCase,
                        scanner,
                        navigator
                ).build();
        this.exchangeMenu = new ExchangeMenu
                (
                        userLogoutUseCase,
                        viewWalletBalanceUseCase,
                        getCurrentUseCase,
                        depositMoneyUseCase,
                        viewTransactionHistoryUseCase,
                        buyCryptoDirectlyUseCase,
                        placeBuyOrderUseCase,
                        placeSellOrderUseCase,
                        scanner,
                        navigator
                ).build();
        this.currentMenu = mainMenu;

        navigator.register(MenuNavigator.MAIN_MENU, mainMenu);
        navigator.register(MenuNavigator.EXCHANGE_MENU, exchangeMenu);

    }

    @Override
    public void boot() {
        System.out.println("\u001b[35m ------------------ Welcome to the Crypto Exchange System -------------------- \u001b[0m");
        while (true) {
            try {
                currentMenu.show();
                out.println("Please, enter the number of the option you want to select: ");
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

    @Override
    public void setCurrentMenu(Menu menu) {
        currentMenu = menu;
    }
}
