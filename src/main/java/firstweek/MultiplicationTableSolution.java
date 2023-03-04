package firstweek;

public class MultiplicationTableSolution {
    public static void main(String[] args) {
        printMultiplicationTable();
    }

    private static void printMultiplicationTable() {
        String format = "%d x %d = %-6d";
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                System.out.printf(format, j, i, j*i);
            }
            System.out.println();
        }
    }
}
