package Utils;

public class Formatter {
    public static String maskAccountNumber(String accountNumber) {
        if (accountNumber.length() != 16) {
            System.out.println("Invalid account number length");
            throw new IllegalArgumentException("Invalid account number length");
        }

        StringBuilder maskedNumber = new StringBuilder();

        for (int i = 0; i < accountNumber.length(); i++) {
            if (i >= 12 || accountNumber.charAt(i) == ' ') {
                maskedNumber.append(accountNumber.charAt(i));
            } else {
                maskedNumber.append('*');
            }

            if ((i + 1) % 4 == 0 && (i + 1) < accountNumber.length()) {
                maskedNumber.append(' ');
            }
        }

        return maskedNumber.toString();
    }

    public static String formatAccountNumber(String accountNumber) {
        if (accountNumber.length() != 16) {
            System.out.println("Invalid account number length");
            throw new IllegalArgumentException("Invalid account number length");
        }

        StringBuilder formattedNumber = new StringBuilder();

        for (int i = 0; i < accountNumber.length(); i++) {
            if (i > 0 && i % 4 == 0) {
                formattedNumber.append(' ');
            }

            formattedNumber.append(accountNumber.charAt(i));
        }

        return formattedNumber.toString();
    }


}
