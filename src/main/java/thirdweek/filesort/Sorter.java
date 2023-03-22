package thirdweek.filesort;

import java.io.*;
import java.util.*;

/**
 * Represents a Sorter class
 * What is does is external sort of big file when we have to memory to do the whole operation in memory itself
 * (say, the available memory is 1 GB and we try to sort the file of 10GB size)
 * <p>
 * Fisrt, what it does is splitting and reading original big file on shunks,
 * preliminary sorting them AND writing to disk to temporary folder.
 * Second, it gathers all the preliminary splitted and internally sorted files, takes the first values from them and
 * merges into one file using PriorityQueue.
 * <p>
 * Lastly it deletes all temp data and closes streams.
 *
 * @author Khasmamedov T.
 * @version 1.9
 */
public class Sorter {
    private static final int CHUNK_SIZE = 200_000; //suppose we have pretty low memory

    public File sortFile(File dataFile) throws IOException {
        List<File> files = preliminarySort(dataFile);
        return mergeFiles(files);
    }

    public static List<File> preliminarySort(File file) throws IOException {
        List<File> files = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<Long> tempList = new ArrayList<>(CHUNK_SIZE);
            String line;
            while ((line = reader.readLine()) != null) {
                tempList.add(Long.valueOf(line));
                for (int i = 0; i < CHUNK_SIZE - 1; i++) {
                    line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    tempList.add(Long.valueOf(line));
                }
                files.add(sortAndSave(tempList));
                tempList.clear();
            }
        }
        return files;
    }


    public static File sortAndSave(List<Long> tempList) throws IOException {
        Collections.sort(tempList);
        File newTmpFile = File.createTempFile("sortInBatch", ".txt");
        newTmpFile.deleteOnExit();
        try (BufferedWriter fbw = new BufferedWriter(new FileWriter(newTmpFile))) {
            for (Long longNumber : tempList) {
                fbw.write(longNumber + "\n");
            }
        }
        return newTmpFile;
    }

    private File mergeFiles(List<File> files) throws IOException {
        File outputFile = new File("output.txt");
        List<BufferedReader> readerList = new ArrayList<>();
        for (File file : files) {
            readerList.add(new BufferedReader(new FileReader(file)));
        }
        PriorityQueue<ComparableRecord> queue = new PriorityQueue<>(readerList.size());
        for (BufferedReader reader : readerList) {
            String line;
            if ((line = reader.readLine()) != null) {
                queue.offer(new ComparableRecord(Long.parseLong(line), queue.size()));
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            while (!queue.isEmpty()) {
                ComparableRecord value = queue.poll();
                writer.write(value.value + "\n");
                BufferedReader reader = readerList.get(value.index);
                String line = reader.readLine();
                if (line != null) {
                    queue.offer(new ComparableRecord(Long.parseLong(line), value.index));
                }
            }
        }
        closeAllStreams(readerList);
        return outputFile;
    }

    private void closeAllStreams(List<BufferedReader> readerList) throws IOException {
        for (BufferedReader bufferedReader : readerList) {
            bufferedReader.close();
        }
    }

    private record ComparableRecord(long value, int index) implements Comparable<ComparableRecord> {
        @Override
        public int compareTo(ComparableRecord o) {
            return Long.compare(this.value, o.value);
        }
    }
}

