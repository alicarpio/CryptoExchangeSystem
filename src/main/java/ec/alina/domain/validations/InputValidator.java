package ec.alina.domain.validations;

public class InputValidator {
    public static boolean isValidNumber(String str) {
        return str != null && str.matches("[0-9.]+");
    }
}
