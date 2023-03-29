package io.ylab.intensive.lesson04.movie;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class MovieLoaderImpl implements MovieLoader {
    private DataSource dataSource;
    private static final String SQL_MOVIE_INSERTION_STATEMENT =
            "INSERT INTO movie (year, length, title, subject, actors, actress, director, popularity, awards) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public MovieLoaderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadData(File file) {
        List<Movie> movieList;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            movieList = parseFilmFileToList(reader);

            batchDataLoad(movieList);
        } catch (IOException msg) {
            System.err.println("get IOException in loadData" + msg);
        } catch (SQLException msg) {
            System.err.println("get SQLException in loadData" + msg);
        }

    }

    private List<Movie> parseFilmFileToList(BufferedReader reader) throws IOException {
        Movie movie;
        List<Movie> movieList = new ArrayList<>();
        String line;
        reader.readLine();
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] stringMovie = line.split(";");
            movie = new Movie();
            movie.setYear(stringMovie[0].isEmpty() ? null : Integer.parseInt(stringMovie[0]));
            movie.setLength(stringMovie[1].isEmpty() ? null : Integer.parseInt(stringMovie[1]));
            movie.setTitle(stringMovie[2].isEmpty() ? null : stringMovie[2]);
            movie.setSubject(stringMovie[3].isEmpty() ? null : stringMovie[3]);
            movie.setActors(stringMovie[4].isEmpty() ? null : stringMovie[4]);
            movie.setActress(stringMovie[5].isEmpty() ? null : stringMovie[5]);
            movie.setDirector(stringMovie[6].isEmpty() ? null : stringMovie[6]);
            movie.setPopularity(stringMovie[7].isEmpty() ? null : Integer.parseInt(stringMovie[7]));
            movie.setAwards(stringMovie[8].isEmpty() ? null : Boolean.parseBoolean(stringMovie[8]));

            movieList.add(movie);
        }
        return movieList;
    }

    private void batchDataLoad(List<Movie> movieList) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_MOVIE_INSERTION_STATEMENT)) {

            for (int i = 0; i < movieList.size(); i++) {
                Movie movie = movieList.get(i);
                int incrementedPositionCounter = 1;

                setupMovieFieldToIntegerType(statement, movie.getYear(), incrementedPositionCounter);
                incrementedPositionCounter++;

                setupMovieFieldToIntegerType(statement, movie.getLength(), incrementedPositionCounter);
                incrementedPositionCounter++;

                setupMovieFieldToVarcharType(statement, movie.getTitle(), incrementedPositionCounter);
                incrementedPositionCounter++;

                setupMovieFieldToVarcharType(statement, movie.getSubject(), incrementedPositionCounter);
                incrementedPositionCounter++;

                setupMovieFieldToVarcharType(statement, movie.getActors(), incrementedPositionCounter);
                incrementedPositionCounter++;

                setupMovieFieldToVarcharType(statement, movie.getActress(), incrementedPositionCounter);
                incrementedPositionCounter++;

                setupMovieFieldToVarcharType(statement, movie.getDirector(), incrementedPositionCounter);
                incrementedPositionCounter++;

                setupMovieFieldToIntegerType(statement, movie.getPopularity(), incrementedPositionCounter);
                incrementedPositionCounter++;

                setupMovieFieldToBooleanType(statement, movie.getAwards(), incrementedPositionCounter);

                statement.addBatch();
            }

            statement.executeBatch();
        }
    }

    private void setupMovieFieldToIntegerType(PreparedStatement statement, Integer integerValue, int position) throws SQLException {
        if (integerValue == null) {
            statement.setNull(position, Types.INTEGER);
        } else {
            statement.setInt(position, integerValue);
        }
    }

    private void setupMovieFieldToVarcharType(PreparedStatement statement, String stringValue, int position) throws SQLException {
        if (stringValue == null) {
            statement.setNull(position, Types.VARCHAR);
        } else {
            statement.setString(position, stringValue);
        }
    }

    private void setupMovieFieldToBooleanType(PreparedStatement statement, Boolean booleanValue, int position) throws SQLException {
        if (booleanValue == null) {
            statement.setNull(position, Types.BOOLEAN);
        } else {
            statement.setBoolean(position, booleanValue);
        }
    }
}
