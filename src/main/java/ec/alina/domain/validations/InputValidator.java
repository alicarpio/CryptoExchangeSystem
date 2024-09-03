package ec.alina.domain.validations;

import ec.alina.domain.enums.CryptoType;

import java.math.BigDecimal;
import java.util.Arrays;

public class InputValidator {
    public static boolean isValidNumber(String str) {
            try {
                new BigDecimal(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
    }

    public static boolean isValidCrypto(String str){
       return Arrays.stream(CryptoType.values()).anyMatch(cryptoType -> cryptoType.name().equals(str));
    }
}
