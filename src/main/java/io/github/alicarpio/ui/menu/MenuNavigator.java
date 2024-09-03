package io.github.alicarpio.ui.menu;

import java.util.*;

public class MenuNavigator {
    public static final String MAIN_MENU = "MAIN_MENU";
    public static final String EXCHANGE_MENU = "EXCHANGE_MENU";

    private final Map<String, Menu> registry = new HashMap<>();
    private final MenuNavigatorHost navigatorHost;
    private final Deque<Menu> navStack = new ArrayDeque<>();

    public MenuNavigator(MenuNavigatorHost navigatorHost) {
        this.navigatorHost = navigatorHost;
    }

    public void register(String route, Menu menu) {
        registry.put(route, menu);
    }

    public void navigateTo(String route) {
        var menu = registry.get(route);
        Objects.requireNonNull(menu, "No menu registered for route: " + route);
        navStack.push(menu);
        navigatorHost.setCurrentMenu(menu);
    }

    public void back() {
        if (navStack.isEmpty()) {
            throw new IllegalArgumentException("Navigation stack is empty!");
        }
        Menu menu = navStack.pop();
        navigatorHost.setCurrentMenu(menu);
    }
}
