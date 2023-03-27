package io.ylab.intensive.lesson04.movie;

import thirdweek.orgstructure.Employee;

import javax.sql.DataSource;
import java.io.*;
import java.sql.*;
import java.util.*;

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
            System.err.println(msg);
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

    private void batchDataLoad(List<Movie> movieList) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_MOVIE_INSERTION_STATEMENT)) {

            for (Movie movie : movieList) {
                Integer movieYear = movie.getYear();
                if (movieYear == null) {
                    statement.setNull(1, Types.INTEGER);
                } else {
                    statement.setInt(1, movieYear);
                }

                Integer movieLength = movie.getLength();
                if (movieLength == null) {
                    statement.setNull(2, Types.INTEGER);
                } else {
                    statement.setInt(2, movieLength);
                }

                String movieTitle = movie.getTitle();
                if (movieTitle == null) {
                    statement.setNull(3, Types.VARCHAR);
                } else {
                    statement.setString(3, movieTitle);
                }

                String movieSubject = movie.getSubject();
                if (movieSubject == null) {
                    statement.setNull(4, Types.VARCHAR);
                } else {
                    statement.setString(4, movieSubject);
                }

                String movieActors = movie.getActors();
                if (movieActors == null) {
                    statement.setNull(5, Types.VARCHAR);
                } else {
                    statement.setString(5, movieActors);
                }

                String movieActress = movie.getActress();
                if (movieActress == null) {
                    statement.setNull(6, Types.VARCHAR);
                } else {
                    statement.setString(6, movieActress);
                }

                String movieDirector = movie.getDirector();
                if (movieDirector == null) {
                    statement.setNull(7, Types.VARCHAR);
                } else {
                    statement.setString(7, movieDirector);
                }

                Integer moviePopularity = movie.getPopularity();
                if (moviePopularity == null) {
                    statement.setNull(8, Types.INTEGER);
                } else {
                    statement.setInt(8, moviePopularity);
                }

                Boolean movieAwards = movie.getAwards();
                if (movieAwards == null) {
                    statement.setNull(9, Types.BOOLEAN);
                } else {
                    statement.setBoolean(9, movieAwards);
                }

                statement.addBatch();
            }

            statement.executeBatch();

        } catch (SQLException msg) {
            System.err.println(msg);
        }
    }
}
