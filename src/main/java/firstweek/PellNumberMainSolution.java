package firstweek;

import java.util.Scanner;

import static firstweek.InputDataHelper.requestPositiveNaturalNumberWithThreshold;

public class PellNumberMainSolution {
    public static final int ZERO = 0;
    public static final int THIRTY = 30;

    public static void main(String args[]) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(getPell(requestPositiveNaturalNumberWithThreshold(scanner, ZERO, THIRTY)));
        }
    }

    public static int getPell(int n) {
        if (n == 0 || n == 1) {
            return n;
        }

        int firstNumber = 0;
        int secondNumber = 1;
        int pellNumber = 1;
        for (int i = 2; i <= n; i++) {
            pellNumber = 2 * secondNumber + firstNumber;
            firstNumber = secondNumber;
            secondNumber = pellNumber;
        }

        return pellNumber;
    }

}
