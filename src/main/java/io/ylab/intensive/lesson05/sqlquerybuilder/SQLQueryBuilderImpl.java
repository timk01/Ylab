package io.ylab.intensive.lesson05.sqlquerybuilder;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SQLQueryBuilderImpl implements SQLQueryBuilder {

    private final DataSource dataSource;
    private DatabaseMetaData databaseMetaData;
    private final String[] tables = {"TABLE", "SYSTEM VIEW", "SYSTEM TABLE"};
    private final StringBuilder querySelectSb = new StringBuilder();
    public static final String TABLE_NAME_LABEL = "TABLE_NAME";
    public static final String COLUMN_NAME_LABEL = "COLUMN_NAME";

    public SQLQueryBuilderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String queryForTable(String tableName) throws SQLException {
        if (tableName == null) {
            throw new SQLException("Name of table cannot have null value");
        }

        List<String> columnNameList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            databaseMetaData = connection.getMetaData();

            if (!isTableExist(databaseMetaData, tableName)) {
                return null;
            } else {
                fillTableWithColumnNames(tableName, columnNameList);

                fillSelectQuery(tableName, columnNameList);
            }
        }

        return querySelectSb.toString();
    }

    @Override
    public List<String> getTables() throws SQLException {
        List<String> stringTables;
        try (Connection connection = dataSource.getConnection()) {

            databaseMetaData = connection.getMetaData();
            ResultSet tablesResultSet = databaseMetaData.getTables(
                    null, null, null, this.tables);

            stringTables = new ArrayList<>();

            while (tablesResultSet.next()) {
                stringTables.add(tablesResultSet.getString(TABLE_NAME_LABEL));
            }

            tablesResultSet.close();
            return stringTables;
        }
    }

    private boolean isTableExist(DatabaseMetaData databaseMetaData, String tableName) throws SQLException {
        try (ResultSet resultSet =
                     databaseMetaData.getTables(null, null, tableName, this.tables)) {

            return resultSet.next();
        }
    }

    private void fillTableWithColumnNames(String tableName, List<String> columnNameList) throws SQLException {
        ResultSet databaseMetaDataColumns =
                databaseMetaData.getColumns(null, null, tableName, null);

        while (databaseMetaDataColumns.next()) {
            columnNameList.add(databaseMetaDataColumns.getString(COLUMN_NAME_LABEL));
        }
    }

    private void fillSelectQuery(String tableName, List<String> columnNameList) {
        querySelectSb.setLength(0);
        querySelectSb.append("SELECT");
        int reducedByOneListSize = columnNameList.size() - 1;

        for (int i = 0; i < reducedByOneListSize; i++) {
            String nameOfColumn = columnNameList.get(i);
            querySelectSb.append(" ").append(nameOfColumn);
            if (i != reducedByOneListSize - 1) {
                querySelectSb.append(",");
            }
        }
        querySelectSb.append(" ").append("FROM").append(" ").append(tableName);
    }
}
