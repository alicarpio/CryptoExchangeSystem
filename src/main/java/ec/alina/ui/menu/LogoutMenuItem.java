package ec.alina.ui.menu;

import ec.alina.domain.use_cases.UserLogoutUseCase;

import static java.lang.System.out;

public class LogoutMenuItem extends MenuItem {


    public LogoutMenuItem(int rowNumber, UserLogoutUseCase userLogoutUseCase, MenuNavigator navigator) {
        super(rowNumber, "Log out", () -> {
            out.println("Good Bye, see you soon!");
            out.println("----------- Log out -----------");
            userLogoutUseCase.invoke();
            navigator.navigateTo(MenuNavigator.MAIN_MENU);
        });
    }

}
