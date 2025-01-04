package com.bitwise.extractcvs.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Utils {

    private static final Locale LOCALE_BRAZIL = new Locale("pt", "BR");

    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastIndex = fileName.lastIndexOf(".");
        return lastIndex == -1 ? "" : fileName.substring(lastIndex + 1);
    }

    public static String formatToCurrency(BigDecimal value) {
        if (value == null) {
            return "R$ 0,00";
        }
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(LOCALE_BRAZIL);
        return currencyFormat.format(value);
    }

    public static BigDecimal parseBigDecimal(String value, String line, String fieldName) {
        try {
            String originalValue = value.trim();

            if (originalValue.isEmpty()) {
                logInvalidLine(fieldName + " vazio", line);
                return null;
            }

            String formattedValue = originalValue;

            formattedValue = formattedValue.replace(".", "");

            if (formattedValue.contains(",")) {
                formattedValue = formattedValue.replace(",", ".");
            }

            BigDecimal parsedValue = new BigDecimal(formattedValue);

            int originalScale = countDecimalPlaces(originalValue);

            if (originalScale > 2) {
                parsedValue = parsedValue.setScale(2, BigDecimal.ROUND_HALF_UP);
            }

            if (!originalValue.equals(formattedValue)) {
                System.out.println("Valor original: " + originalValue + " -> Valor formatado: " + formattedValue);
            }

            return parsedValue;
        } catch (NumberFormatException e) {
            logInvalidLine(fieldName + " inválido", line);
            return null;
        }
    }

    public static int countDecimalPlaces(String value) {
        value = value.trim();

        if (value.contains(",")) {
            String[] parts = value.split(",");
            if (parts.length > 1) {
                return parts[1].length();
            }
        } else if (value.contains(".")) {
            String[] parts = value.split("\\.");
            if (parts.length > 1) {
                return parts[1].length();
            }
        }
        return 0;
    }

    public static void logInvalidLine(String message, String line) {
        System.out.println(message + ": " + line);
    }
}
