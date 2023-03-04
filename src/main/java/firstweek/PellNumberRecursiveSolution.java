package firstweek;

import java.util.Scanner;

import static firstweek.InputDataHelper.requestPositiveNaturalNumberWithThreshold;

public class PellNumberRecursiveSolution {
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
        return 2 * getPell(n - 1) + getPell(n - 2);
    }
}
