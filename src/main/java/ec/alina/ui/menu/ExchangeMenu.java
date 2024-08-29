package ec.alina.ui.menu;

import ec.alina.domain.use_cases.UserLogoutUseCase;

public class ExchangeMenu extends Menu {
    private final UserLogoutUseCase userLogoutUseCase;
    private final MenuNavigator navigator;

    public ExchangeMenu(UserLogoutUseCase userLogoutUseCase, MenuNavigator navigator) {
        super("Crypto Exchange Menu");
        this.userLogoutUseCase = userLogoutUseCase;
        this.navigator = navigator;
    }

    @Override
    public Menu build() {
        addItem(new LogoutMenuItem(3, userLogoutUseCase, navigator));
        return this;
    }


}
