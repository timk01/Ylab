package firstweek;

import java.util.Scanner;

public class InputDataHelper {
    public static final int ZERO = 0;

    static int requestPositiveNumber(Scanner scanner) {
        int n = -1;

        boolean isGoodInput;
        do {
            if (scanner.hasNextInt()) {
                n = scanner.nextInt();
                scanner.nextLine();
                if (n < ZERO) {
                    System.out.println("Число должно быть неотрицательным!");
                    isGoodInput = false;
                } else {
                    isGoodInput = true;
                }
            } else {
                System.out.println("Это не число\n");
                scanner.nextLine();
                isGoodInput = false;
            }
        } while (!isGoodInput);

        return n;
    }

    static int requestPositiveNaturalNumberWithThreshold(Scanner scanner, int lowerThreshold, int upperThreshold) {
        int n = -1;

        boolean isGoodInput;
        do {
            if (scanner.hasNextInt()) {
                n = scanner.nextInt();
                scanner.nextLine();
                if (n < lowerThreshold || n > upperThreshold) {
                    System.out.println("Число должно быть больше чем " + lowerThreshold + " и меньше или равным " + upperThreshold);
                    isGoodInput = false;
                } else {
                    isGoodInput = true;
                }
            } else {
                System.out.println("Это не число\n");
                scanner.nextLine();
                isGoodInput = false;
            }
        } while (!isGoodInput);

        return n;
    }

    static String requestCharacter(Scanner scanner) {
        String str = "";

        boolean isGoodInput;
        do {
            if (scanner.hasNext()) {
                str = scanner.next();
                scanner.nextLine();
                if (str == null || str.isEmpty()) {
                    System.out.println("строчка null/пуста");
                    isGoodInput = false;
                } else if (str.length() != 1) {
                    System.out.println("строчка более 1 символа");
                    isGoodInput = false;
                } else {
                    isGoodInput = true;
                }
            } else {
                System.out.println("Нет ввода\n");
                scanner.nextLine();
                isGoodInput = false;
            }
        } while (!isGoodInput);

        return str;
    }
}
