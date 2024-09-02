package ec.alina.ui.menu;

import ec.alina.domain.use_cases.UserLogoutUseCase;

import static java.lang.System.out;

public class LogoutMenuItem extends MenuItem {


    public LogoutMenuItem(int rowNumber, UserLogoutUseCase userLogoutUseCase, MenuNavigator navigator) {
        super(rowNumber, "Log out", () -> {
            out.println("\u001b[44m  Good Bye, see you soon!  \u001b[0m");
            out.println("----------- Log out -----------");
            userLogoutUseCase.invoke();
            navigator.navigateTo(MenuNavigator.MAIN_MENU);
        });
    }

}
