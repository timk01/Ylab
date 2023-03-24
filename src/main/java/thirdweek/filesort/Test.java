package thirdweek.filesort;

import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        File dataFile = new Generator().generate("data.txt", 3_001_001);
        System.out.println(new Validator(dataFile).isSorted());

        File sortedFile = new Sorter().sortFile(dataFile);
        System.out.println(new Validator(sortedFile).isSorted());
    }
}