package ec.alina.ui.menu;

import ec.alina.domain.enums.CrytoType;
import ec.alina.domain.models.User;
import ec.alina.domain.models.Wallet;
import ec.alina.domain.use_cases.GetCurrentUseCase;
import ec.alina.domain.use_cases.UserLogoutUseCase;
import ec.alina.domain.use_cases.ViewWalletBalanceUseCase;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentMap;

import static java.lang.System.out;


public class ExchangeMenu extends Menu {
    private final UserLogoutUseCase userLogoutUseCase;
    private final ViewWalletBalanceUseCase viewWalletBalanceUseCase;
    private final GetCurrentUseCase getCurrentUseCase;
    private final MenuNavigator navigator;

    public ExchangeMenu(UserLogoutUseCase userLogoutUseCase, ViewWalletBalanceUseCase viewWalletBalanceUseCase, GetCurrentUseCase getCurrentUseCase, MenuNavigator navigator) {
        super("Crypto Exchange Menu");
        this.userLogoutUseCase = userLogoutUseCase;
        this.viewWalletBalanceUseCase = viewWalletBalanceUseCase;
        this.getCurrentUseCase = getCurrentUseCase;
        this.navigator = navigator;
    }

    @Override
    public Menu build() {
        addItem(new MenuItem(1, "View wallet balance", this::onViewWalletBalance));
        addItem(new MenuItem(2, "Deposit", () -> out.println("Deposit")));
        addItem(new LogoutMenuItem(3, userLogoutUseCase, navigator));
        return this;
    }

    private void onViewWalletBalance() {
        int index = 1;
        out.println("---------------- Wallet balance -----------------");
        out.println(" ");
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

    
}
