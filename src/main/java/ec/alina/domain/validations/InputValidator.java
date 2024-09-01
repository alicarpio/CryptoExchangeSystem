package ec.alina.domain.validations;

import java.math.BigDecimal;

public class InputValidator {
    public static boolean isValidNumber(String str) {
            try {
                new BigDecimal(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
    }
}
