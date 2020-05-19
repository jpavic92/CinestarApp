/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.cinestar.dal.sql;

import hr.cinestar.dal.Repository;
import hr.cinestar.model.Genre;
import hr.cinestar.model.Movie;
import hr.cinestar.model.MovieRole;
import hr.cinestar.model.Person;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.sql.DataSource;

/**
 *
 * @author Josip
 */
public class SqlRepository implements Repository {

    //SQL table column names
    private static final String MOVIE_ID = "IDMovie";
    private static final String MOVIE_TITLE = "Title";
    private static final String MOVIE_DESCRIPTION = "Description";
    private static final String MOVIE_ORIGTITLE = "OriginalTitle";
    private static final String MOVIE_POSTERPATH = "PosterPath";
    private static final String MOVIE_LINK = "Link";
    private static final String MOVIE_BEGINNINGDATE = "BeginningDate";

    private static final String PERSON_ID = "IDPerson";
    private static final String PERSON_FIRSTNAME = "FirstName";
    private static final String PERSON_LASTNAME = "LastName";

    private static final String GENRE_ID = "IDGenre";
    private static final String GENRE_NAME = "GenreName";

    //SQL procedures calls
    //Movie
    private static final String CREATE_MOVIE = "{ CALL createMovie (?,?,?,?,?,?,?) }";
    private static final String UPDATE_MOVIE = "{ CALL updateMovie (?,?,?,?,?,?,?) }";
    private static final String DELETE_MOVIE = "{ CALL deleteMovie (?) }";
    private static final String SELECT_MOVIE = "{ CALL selectMovie (?) }";
    private static final String SELECT_MOVIES = "{ CALL selectMovies () }";

    //Person
    private static final String CREATE_PERSON = "{ CALL createPerson (?,?,?) }";
    private static final String UPDATE_PERSON = "{ CALL updatePerson (?,?,?) }";
    private static final String DELETE_PERSON = "{ CALL deletePerson (?) }";
    private static final String SELECT_PERSON = "{ CALL selectPerson (?) }";
    private static final String SELECT_PERSONS = "{ CALL selectPersons() }";

    private static final String CREATE_MOVIE_INVOLVEMENT = "{ CALL createMovieInvolvement(?,?,?) }";
    private static final String DELETE_MOVIE_INVOLVEMENT = "{ CALL deleteMovieInvolvement(?,?,?) }";
    private static final String SELECT_MOVIE_INVOLVEMENTS_BY_ROLEID = "{ CALL selectMovieInvolvementsByRoleId(?, ?) }"; //fetch all directors or actors of specified movie
    private static final String SELECT_INVOLVEMENTS = "{ CALL selectInvolvements() }"; //fetch all persons with involvements

    private static final String CREATE_GENRE = "{ CALL createGenre (?, ?) }";
    private static final String SELECT_MOVIE_GENRES = "{ CALL selectMovieGenres(?) }";
    private static final String SELECT_GENRES = "{ CALL selectGenres() }";
    private static final String CREATE_MOVIEGENRE = "{ CALL createMovieGenre(?,?) }";
    
    private static final String DELETE_ALL_DATA = "{ CALL deleteAllData () }";
    
    //CRUD MOVIE
    @Override
    public int createMovie(Movie movie) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();

        try (Connection con = ds.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {

            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getDescription());
            stmt.setString(3, movie.getOriginalTitle());
            stmt.setString(4, movie.getPosterPath());
            stmt.setString(5, movie.getLink());
            stmt.setString(6, Movie.DATE_FORMAT.format(movie.getBeginningDate()));

            stmt.registerOutParameter(7, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(7);
        }
    }

    @Override
    public void updateMovie(int id, Movie data) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();

        try (Connection con = ds.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_MOVIE)) {

            stmt.setInt(1, id);
            stmt.setString(2, data.getTitle());
            stmt.setString(3, data.getDescription());
            stmt.setString(4, data.getOriginalTitle());
            stmt.setString(5, data.getPosterPath());
            stmt.setString(6, data.getLink());
            stmt.setString(7, Movie.DATE_FORMAT.format(data.getBeginningDate()));

            stmt.executeUpdate();

        }
    }

    @Override
    public void createMovies(List<Movie> movies) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();

        try (Connection con = ds.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {

            for (Movie movie : movies) {
                stmt.setString(1, movie.getTitle());
                stmt.setString(2, movie.getDescription());
                stmt.setString(3, movie.getOriginalTitle());
                stmt.setString(4, movie.getPosterPath());
                stmt.setString(5, movie.getLink());
                stmt.setString(6, Movie.DATE_FORMAT.format(movie.getBeginningDate()));
                stmt.registerOutParameter(7, Types.INTEGER);
                stmt.executeUpdate();

            }
        }
    }

    @Override
    public void deleteMovie(int id) throws Exception {

        DataSource ds = DataSourceSingleton.getInstance();

        try (Connection con = ds.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_MOVIE)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Movie> selectMovie(int id) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();
        List<Person> directors;
        List<Person> actors;
        List<Genre> genres;

        try (Connection con = ds.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIE)) {

            directors = selectMovieInvolvementsByRole(id, MovieRole.DIRECTOR.getRole(), con);
            actors = selectMovieInvolvementsByRole(id, MovieRole.ACTOR.getRole(), con);
            genres = selectMovieGenres(id, con);

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    return Optional.of(new Movie(
                            rs.getInt(MOVIE_ID),
                            rs.getString(MOVIE_TITLE),
                            rs.getString(MOVIE_DESCRIPTION),
                            rs.getString(MOVIE_ORIGTITLE),
                            rs.getString(MOVIE_POSTERPATH),
                            rs.getString(MOVIE_LINK),
                            Movie.DATE_FORMAT.parse(rs.getString(MOVIE_BEGINNINGDATE)),
                            directors,
                            actors,
                            genres));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Movie> selectMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        List<Person> directors;
        List<Person> actors;
        List<Genre> genres;

        DataSource ds = DataSourceSingleton.getInstance();

        try (Connection con = ds.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIES);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int movieId = rs.getInt(MOVIE_ID);

                directors = selectMovieInvolvementsByRole(movieId, MovieRole.DIRECTOR.getRole(), con);
                actors = selectMovieInvolvementsByRole(movieId, MovieRole.ACTOR.getRole(), con);
                genres = selectMovieGenres(movieId, con);

                movies.add(new Movie(
                        movieId,
                        rs.getString(MOVIE_TITLE),
                        rs.getString(MOVIE_DESCRIPTION),
                        rs.getString(MOVIE_ORIGTITLE),
                        rs.getString(MOVIE_POSTERPATH),
                        rs.getString(MOVIE_LINK),
                        Movie.DATE_FORMAT.parse(rs.getString(MOVIE_BEGINNINGDATE)),
                        directors,
                        actors,
                        genres));
            }
        }
        return movies;
    }

    //CRUD PERSON
    @Override
    public int createPerson(Person person) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();

        try (Connection con = ds.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_PERSON)) {

            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getLastName());

            stmt.registerOutParameter(3, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(3);
        }
    }

    @Override
    public void createPersons(Set<Person> persons) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();

        try (Connection con = ds.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_PERSON)) {

            for (Person person : persons) {
                stmt.setString(1, person.getFirstName());
                stmt.setString(2, person.getLastName());
                stmt.registerOutParameter(3, Types.INTEGER);
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void updatePerson(int id, Person data) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();

        try (Connection con = ds.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_PERSON)) {

            stmt.setInt(1, id);
            stmt.setString(2, data.getFirstName());
            stmt.setString(3, data.getLastName());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deletePerson(int id) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();

        try (Connection con = ds.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_PERSON)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Person> selectPerson(int id) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();

        try (Connection con = ds.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_PERSON)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Person(
                            id,
                            rs.getString(PERSON_FIRSTNAME),
                            rs.getString(PERSON_LASTNAME)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Person> selectPersons() throws Exception {
        List<Person> persons = new ArrayList<>();

        DataSource ds = DataSourceSingleton.getInstance();

        try (Connection con = ds.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_PERSONS);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                persons.add(new Person(
                        rs.getInt(PERSON_ID),
                        rs.getString(PERSON_FIRSTNAME),
                        rs.getString(PERSON_LASTNAME)));
            }
        }

        return persons;
    }

    //CRUD GENRE
    @Override
    public void createGenres(Set<Genre> genres) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();

        try (Connection con = ds.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_GENRE)) {

            for (Genre genre : genres) {
                stmt.setString(1, genre.getName());
                stmt.executeUpdate();
            }
        }
    }

    private List<Genre> selectMovieGenres(int movieId, Connection con) throws SQLException {
        List<Genre> genres = new ArrayList<>();

        try (CallableStatement stmt = con.prepareCall(SELECT_MOVIE_GENRES)) {

            stmt.setInt(1, movieId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    genres.add(new Genre(rs.getInt(GENRE_ID), rs.getString(GENRE_NAME)));
                }
            }
        }
        return genres;
    }

    @Override
    public List<Genre> selectGenres() throws Exception {
        List<Genre> genres = new ArrayList<>();
        DataSource ds = DataSourceSingleton.getInstance();

        try (Connection con = ds.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_GENRES);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                genres.add(new Genre(
                        rs.getInt(GENRE_ID),
                        rs.getString(GENRE_NAME)));
            }
        }

        return genres;
    }

    //CRUD INVOLVEMENTS
    @Override
    public void createMovieInvolvements(int movieId, List<Person> directors, List<Person> actors) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();

        try (Connection con = ds.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE_INVOLVEMENT)) {

            for (Person director : directors) {
                stmt.setInt(1, movieId);
                stmt.setInt(2, director.getId());
                stmt.setInt(3, MovieRole.DIRECTOR.getRole());
                stmt.executeUpdate();
            }

            for (Person actor : actors) {
                stmt.setInt(1, movieId);
                stmt.setInt(2, actor.getId());
                stmt.setInt(3, MovieRole.ACTOR.getRole());
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void deleteMovieInvolvement(int movieId, int personId, int roleId) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();

        try (Connection con = ds.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_MOVIE_INVOLVEMENT)) {

            stmt.setInt(1, movieId);
            stmt.setInt(2, personId);
            stmt.setInt(3, roleId);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Person> selectMovieInvolvementsByRoleId(int movieId, int roleId) throws Exception {
        List<Person> roles = new ArrayList<>();

        DataSource ds = DataSourceSingleton.getInstance();

        try (Connection con = ds.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIE_INVOLVEMENTS_BY_ROLEID)) {

            stmt.setInt(1, movieId);
            stmt.setInt(2, roleId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    roles.add(new Person(
                            rs.getInt(PERSON_ID),
                            rs.getString(PERSON_FIRSTNAME),
                            rs.getString(PERSON_LASTNAME)));
                }
            }
        }
        return roles;
    }

    @Override
    public List<Person> selectInvolvements() throws Exception {
        List<Person> movieRoles = new ArrayList<>();

        DataSource ds = DataSourceSingleton.getInstance();

        try (Connection con = ds.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_INVOLVEMENTS);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                movieRoles.add(new Person(
                        rs.getInt(PERSON_ID),
                        rs.getString(PERSON_FIRSTNAME),
                        rs.getString(PERSON_LASTNAME)));
            }
        }
        return movieRoles;
    }

    private List<Person> selectMovieInvolvementsByRole(int movieId, int roleId, Connection con) throws SQLException {
        List<Person> directors = new ArrayList<>();

        try (CallableStatement stmt = con.prepareCall(SELECT_MOVIE_INVOLVEMENTS_BY_ROLEID)) {

            stmt.setInt(1, movieId);
            stmt.setInt(2, roleId);
            try (ResultSet rs = stmt.executeQuery();) {

                while (rs.next()) {
                    directors.add(new Person(
                            rs.getString(PERSON_FIRSTNAME),
                            rs.getString(PERSON_LASTNAME)));
                }
            }
        }
        return directors;
    }

    @Override
    public void createMovieGenre(int movieId, Set<Genre> genres) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();

        try (Connection con = ds.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIEGENRE)) {

            for (Genre genre : genres) {
                stmt.setInt(1, movieId);
                stmt.setInt(2, genre.getId());
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void initialEntityCreation(Set<Person> persons, Set<Genre> genres, List<Movie> movies) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();

        try (Connection con = ds.getConnection()) {
            
            //Before initial creation of entities and relations delete all previous data
            try(CallableStatement stmt = con.prepareCall(DELETE_ALL_DATA)){
            stmt.executeUpdate();
            }
            
            try (CallableStatement stmt = con.prepareCall(CREATE_PERSON)) {
                for (Person person : persons) {
                    stmt.setString(1, person.getFirstName());
                    stmt.setString(2, person.getLastName());
                    stmt.registerOutParameter(3, Types.INTEGER);
                    stmt.executeUpdate();
                    person.setId(stmt.getInt(3)); //-> procedura vraća ID -> dati ID objektu
                }
            }

            try (CallableStatement stmt = con.prepareCall(CREATE_GENRE)) {
                for (Genre genre : genres) {
                    stmt.setString(1, genre.getName());
                    stmt.registerOutParameter(2, Types.INTEGER);
                    stmt.executeUpdate();
                    genre.setId(stmt.getInt(2));
                }
            }

            try (CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {
                for (Movie movie : movies) {
                    stmt.setString(1, movie.getTitle());
                    stmt.setString(2, movie.getDescription());
                    stmt.setString(3, movie.getOriginalTitle());
                    stmt.setString(4, movie.getPosterPath());
                    stmt.setString(5, movie.getLink());
                    stmt.setString(6, Movie.DATE_FORMAT.format(movie.getBeginningDate()));

                    stmt.registerOutParameter(7, Types.INTEGER);
                    stmt.executeUpdate();
                    movie.setId(stmt.getInt(7));
                }
            }

            setInitalEntityRelations(con, movies, persons, genres);
        }
    }

    private void setInitalEntityRelations(Connection con, List<Movie> movies, Set<Person> persons, Set<Genre> genres) throws SQLException {
        
        //put persons into HashMap for more efficient search
        HashMap<Integer, Person> hmPersons = new HashMap<>();
        
        for (Person person : persons) {
            hmPersons.put(Objects.hash(person.getFirstName(), person.getLastName()), person);
        }
        
        try (CallableStatement stmt = con.prepareCall(CREATE_MOVIE_INVOLVEMENT)) {
                int personId;
                Integer personHashKey;
                
            for (Movie movie : movies) {
                if (movie.getDirectors() != null) {
                    for (Person director : movie.getDirectors()) {
                        personHashKey = Objects.hash(director.getFirstName(), director.getLastName());
                        personId = hmPersons.get(personHashKey).getId();

                        stmt.setInt(1, movie.getId());
                        stmt.setInt(2, personId);
                        stmt.setInt(3, MovieRole.DIRECTOR.getRole());
                        stmt.executeUpdate();
                    }
                }

                if (movie.getActors() != null) {
                    for (Person actor : movie.getActors()) {
                        personHashKey = Objects.hash(actor.getFirstName(), actor.getLastName());
                        personId = hmPersons.get(personHashKey).getId();
                        
                        stmt.setInt(1, movie.getId());
                        stmt.setInt(2, personId);
                        stmt.setInt(3, MovieRole.ACTOR.getRole());
                        stmt.executeUpdate();
                    }
                }
            }
        }

        //napraviti proceduru createMovieGenre!!
        try (CallableStatement stmt = con.prepareCall(CREATE_MOVIEGENRE)) {
            int genreId;
            
            for (Movie movie : movies) {
                if (!movie.getGenre().isEmpty()) {
                    for (Genre genre : movie.getGenre()) {
                        genreId = genres
                                .stream()
                                .filter(g -> g.equals(genre))
                                .findAny()
                                .get()
                                .getId();
                        
                        stmt.setInt(1, movie.getId());
                        stmt.setInt(2, genreId);
                        stmt.executeUpdate();
                    }
                }
            }
        }
    }
    
    

}
