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
            repo.initialEntityCreation(CinestarParser.parsePersons(), CinestarParser.parseGenres(), CinestarParser.parseMovies());
          
        } catch (Exception ex) {
            Logger.getLogger(CinestarApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}

   
    

