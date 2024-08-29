package ec.alina.ui.menu;

public class MenuItem {
    private int rowNumber;
    private String description;
    private MenuItemAction action;

    public MenuItem(int rowNumber, String description, MenuItemAction action) {
        this.rowNumber = rowNumber;
        this.description = description;
        this.action = action;
    }

    public void activate() {
        action.onActivate();
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public String getDescription() {
        return description;
    }

    public MenuItemAction getAction() {
        return action;
    }
}
