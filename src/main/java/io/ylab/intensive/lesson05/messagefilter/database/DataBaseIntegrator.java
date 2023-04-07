package io.ylab.intensive.lesson05.messagefilter.database;

import java.sql.SQLException;

public interface DataBaseIntegrator {
    void fillDbWithBadWords() throws SQLException;

    boolean isTheWordBad(String aBadWordForSearch) throws SQLException;
}
