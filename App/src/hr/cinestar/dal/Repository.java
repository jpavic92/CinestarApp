/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.cinestar.dal;

import hr.cinestar.model.Genre;
import hr.cinestar.model.Movie;
import hr.cinestar.model.Person;
import hr.cinestar.model.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Josip
 */
public interface Repository {
    //CRUD movie
    int createMovie(Movie movie) throws Exception;
    void createMovies(List<Movie> movies) throws Exception;
    void updateMovie(int id, Movie data) throws Exception;
    void deleteMovie(int id) throws Exception;
    Optional<Movie> selectMovie(int id) throws Exception;
    List<Movie> selectMovies() throws Exception;
    List<Integer> selectMoviesIdByPersonRole(int personId, int roleId) throws Exception;
    
    //CRUD person
    int createPerson(Person person) throws Exception;
    void createPersons(Set<Person> persons) throws Exception;
    void updatePerson(int id, Person data) throws Exception;
    void deletePerson(int id) throws Exception;
    Optional<Person> selectPerson(int id) throws Exception;
    List<Person> selectPersons() throws Exception; 
    
    //Genres
    int createGenre(Genre genre) throws Exception; // map
    List<Genre> selectGenres() throws Exception;
    void createMovieGenres(int movieId, List<Genre> genres) throws Exception;
    
    //MovieRoles
    void createMovieInvolvements(int movieId, List<Person> directors, List<Person> actors) throws Exception;
    void createPersonsInvolvements(int personId, List<Movie> directed, List<Movie> acted) throws Exception;
    void deleteMovieInvolvement(int movieId, int personId, int roleId) throws Exception;
    List<Person> selectMovieInvolvementsByRoleId(int movieId, int roleId) throws Exception;
    List<Person> selectInvolvements() throws Exception;
    
    void initialEntityCreation(Set<Person> persons, Set<Genre> genres, List<Movie> movies) throws Exception;
    
    //AppUser
    List<User> selectUsers() throws Exception;
    void createUser(User user) throws Exception;
    int userExits(User user) throws Exception;
    boolean usernameExits(String username) throws Exception;
    
    void deleteAllData() throws Exception;
}
