/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinestarapp;

import hr.cinestar.dal.Repository;
import hr.cinestar.dal.RepositoryFactory;
import hr.cinestar.model.CinestarParser;
import hr.cinestar.model.Genre;
import hr.cinestar.model.Movie;
import hr.cinestar.model.Person;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author Josip
 */
public class CinestarApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Repository repo = RepositoryFactory.getRepository();
        
        
        try {
            loadData(repo);
          
        } catch (Exception ex) {
            Logger.getLogger(CinestarApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void loadData(Repository repo) throws IOException, XMLStreamException, Exception {
        List<Person> dbPersons;
        List<Genre> dbGenres;    
        List<Movie> dbMovies;
        List<Movie> rssMovies;
        
        
        //loadDatabase(repo);
        
        dbPersons = repo.selectPersons();
        //dbGenres = repo.selectGenres();
        dbMovies = repo.selectMovies();
        rssMovies = CinestarParser.parseMovies();
        
        
        for (Movie movie : rssMovies) {
            if (movie.getDirectors() != null) {
                dbMovies.stream()
                        .filter(dbMovie -> dbMovie.equals(movie))
                        .findAny()
                        .get()
                        .setDirectors(movie.getDirectors());
                
            }
            if (movie.getActors() != null) {
                dbMovies.stream()
                        .filter(dbMovie -> dbMovie.equals(movie))
                        .findAny()
                        .get()
                        .setActors(movie.getActors());
            }
            
            if (movie.getGenre() != null) {
                dbMovies.stream()
                        .filter(dbMovie -> dbMovie.equals(movie))
                        .findAny()
                        .get()
                        .setGenre(movie.getGenre());
            }
        }
        
        for (Movie movie : dbMovies) {
            repo.createMovieInvolvements(movie.getId(), intersectPersons(dbPersons, movie.getDirectors()), intersectPersons(dbPersons, movie.getActors()));
        }

        
    }

    private static List<Person> intersectPersons(List<Person> dbPersons, List<Person> actors) {
        List<Person> intersection = new ArrayList<>(dbPersons);
        intersection.retainAll(actors);
        return intersection;
    }

    private static List<Movie> intersectMovies(List<Movie> dbMovies, List<Movie> parsedMovies) {
        List<Movie> intersection = new ArrayList(dbMovies);
        intersection.retainAll(parsedMovies);
        return intersection;
    }

    private static void loadDatabase(Repository repo) throws IOException, XMLStreamException, Exception {
        // smanjiti na jedno otvaranje konekcije
        repo.createPersons(CinestarParser.parsePersons());
        repo.createGenres(CinestarParser.parseGenres());
        repo.createMovies(CinestarParser.parseMovies());
    }
    
}

   
    

