package io.ylab.intensive.lesson04.filesort;

import io.ylab.intensive.lesson04.DbUtil;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class FileSorterTest {
    public static void main(String[] args) throws SQLException, IOException {
        File dataFile = new Generator().generate("data.txt", 1_000_000);
        DataSource dataSource = initDb();
        FileSorter fileSorter = new FileSortImpl(dataSource);

        //реализация пункта 5 задания, на не слишком большой выборке (10000); уже видна разница: 702 С и 2596 БЕЗ
        //не стал выносить общие методы, т.к. по заданию "Реализовать версию без batch-processing" (версию сортера)
        long beforeBatchFilling = System.currentTimeMillis();
        File res = fileSorter.sort(dataFile);
        long afterBatchFilling = System.currentTimeMillis();
        System.out.println("batchInsertDifference time: " + (afterBatchFilling - beforeBatchFilling));

        FileSortImpl fileSorter2 = new FileSortImpl(dataSource);
        beforeBatchFilling = System.currentTimeMillis();
        File res2 = fileSorter2.sortWithoutBatch(dataFile);
        afterBatchFilling = System.currentTimeMillis();
        System.out.println("soloInsertDifference time: " + (afterBatchFilling - beforeBatchFilling));
    }

    public static DataSource initDb() throws SQLException {
        String createSortTable = ""
                + "drop table if exists numbers;"
                + "CREATE TABLE if not exists numbers (\n"
                + "\tval bigint\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createSortTable, dataSource);
        return dataSource;
    }
}
