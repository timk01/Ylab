package firstweek;

import java.util.Random;
import java.util.Scanner;

import static firstweek.InputDataHelper.requestPositiveNaturalNumberWithThreshold;

public class GuessSolution {
    private static final int LOWER_THRESHOLD = 0;
    private static final int UPPER_THRESHOLD = 99;
    private static int maxUserAttempts = 10;

    public static void main(String[] args) {
        int number = new Random().nextInt(UPPER_THRESHOLD) + 1;
        System.out.println("Я загадал число. У тебя " + maxUserAttempts + " попыток угадать.");

        numberGuessGame(number);
    }

    private static void numberGuessGame(int number) {
        int userAttempts = 0;
        Scanner scanner = new Scanner(System.in);
        while (maxUserAttempts > 0) {
            int userGuessedNumber = requestPositiveNaturalNumberWithThreshold(scanner, LOWER_THRESHOLD, UPPER_THRESHOLD);
            userAttempts++;
            if (userGuessedNumber == number) {
                System.out.println("Ты угадал с " + userAttempts + " попытки");
                break;
            } else if (userGuessedNumber > number) {
                System.out.println("Мое число меньше! У тебя осталось " + --maxUserAttempts + " попыток");
            } else {
                System.out.println("Мое число больше! У тебя осталось " + --maxUserAttempts + " попыток");
            }
            if (maxUserAttempts == 0) {
                System.out.print("Ты не угадал");
            }
        }
    }
}
