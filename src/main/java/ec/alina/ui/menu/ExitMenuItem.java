package ec.alina.ui.menu;

import static java.lang.System.out;

public class ExitMenuItem extends MenuItem {
    public ExitMenuItem(int rowNumber) {
        super(rowNumber, "Exit", () -> {
            out.println("\u001b[44m  Good Bye :)  \u001b[0m");
            System.exit(69);
        });
    }
}
