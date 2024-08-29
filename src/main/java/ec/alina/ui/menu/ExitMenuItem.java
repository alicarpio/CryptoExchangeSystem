package ec.alina.ui.menu;

public class ExitMenuItem extends MenuItem {
    public ExitMenuItem(int rowNumber) {
        super(rowNumber, "Exit", () -> System.exit(69));
    }
}
