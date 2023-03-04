package firstweek;

import java.util.Scanner;

import static firstweek.InputDataHelper.requestCharacter;
import static firstweek.InputDataHelper.requestPositiveNumber;

public class StarsSolution {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = requestPositiveNumber(scanner);
            int m = requestPositiveNumber(scanner);
            String template = requestCharacter(scanner);
            printChar(n, m, template);
        }
    }

    private static void printChar(int n, int m, String template) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(template);
            }
            System.out.println();
        }
    }
}
