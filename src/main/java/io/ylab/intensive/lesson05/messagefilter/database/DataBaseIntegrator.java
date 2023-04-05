package io.ylab.intensive.lesson05.messagefilter.database;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface DataBaseIntegrator {

    void fillDbWithBadWords() throws SQLException;
    List<String> findAllBadWords() throws SQLException;
}
