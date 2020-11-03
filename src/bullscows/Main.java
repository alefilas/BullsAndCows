package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static boolean isWin = false;
    static String secretCode;

    public static void main(String[] args) {

        try {
            startGame();
        } catch (NumberFormatException e) {
            String wrongInput = e.getMessage().substring(e.getMessage().indexOf('\"'));
            System.out.println("Error: " + wrongInput + " isn't a valid number.");
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        int count = 1;
        while (!isWin) {
            System.out.println("Turn " + count);
            checkCode();
            count++;
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }

    private static void startGame() throws Exception {

        System.out.println("Please, enter the secret code's length:");
        String input = scanner.next();
        int length = Integer.parseInt(input);

        System.out.println("Input the number of possible symbols in the code:");
        input = scanner.next();
        int numberOfSymbols = Integer.parseInt(input);

        if (length > 36 || numberOfSymbols > 36 || length < 1 || numberOfSymbols < 1) {
            throw new Exception("Error: can't generate a secret code. Length or" +
                    " number of characters is greater than the maximum or lower than minimum");
        } else if (length > numberOfSymbols) {
            throw new Exception(String.format("Error: it's not possible to generate" +
                            " a code with a length of %d with %d unique symbols.",
                    length, numberOfSymbols));
        } else {
            secretCode = generateCode(length, numberOfSymbols);
        }
    }

    public static String generateCode(int length, int numberOfSymbols){

        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            char symbol = Character.forDigit(random.nextInt(numberOfSymbols), numberOfSymbols);
            if (!code.toString().contains(String.valueOf(symbol))) {
                code.append(symbol);
            } else {
                i--;
            }
        }

        printCodeLength(length, numberOfSymbols, code);

        return code.toString();
    }

    public static void printCodeLength(int length, int numberOfSymbols, StringBuilder code) {

        StringBuilder symbols = new StringBuilder(" (0-");

        if (numberOfSymbols <= 10){
            symbols.append(numberOfSymbols - 1).append(").");
        } else {
            symbols.append("9), (a-").append(Character.forDigit(numberOfSymbols - 1, numberOfSymbols))
                    .append(").");
        }

        System.out.println("The secret is prepared: " + "*".repeat(length) + symbols);
    }

    public static void checkCode() {
        int cows = 0;
        int bulls = 0;

        String code = scanner.next();

        for (int i = 0; i < code.length(); i++) {
            if (secretCode.charAt(i) == code.charAt(i)) {
                bulls++;
            } else if (secretCode.contains(String.valueOf(code.charAt(i)))) {
                cows++;
            }
        }

        printCowsAndBulls(cows, bulls);
    }

    public static void printCowsAndBulls(int cows, int bulls) {

        StringBuilder builder = new StringBuilder("Grade: ");

        if (bulls > 0) {
            builder.append(bulls).append(" bull(s)");
        }
        if (bulls > 0 && cows > 0) {
            builder.append(" and ");
        }
        if (cows > 0) {
            builder.append(cows).append(" cow(s)");
        }
        if (bulls == 0 && cows == 0) {
            builder.append("None");
        }

        if (bulls == secretCode.length()) {
            isWin = true;
        }

        System.out.println(builder);
    }

}
