package io.ylab.intensive.lesson05.messagefilter;

import java.io.*;

public class Splitter {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("bad_words.txt"));
             FileWriter fileWriter = new FileWriter("lined_bad_words.txt")) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] splittedString = line.split(",");
                for (String s : splittedString) {
                    String trimmedString = s.trim();
                    if (!trimmedString.contains(" ")) {
                        fileWriter.write(trimmedString + "\n");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
