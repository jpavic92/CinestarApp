/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.cinestar.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Josip
 */
@XmlRootElement (name = "movies")
public class Movies {
    
    private List<Movie> movies;
    
    public Movies(){}
    
    public Movies(List<Movie> movies){
        this.movies = movies;
    }

    @XmlElement(name = "movie")
    public List<Movie> getMovies() {
        return movies;
    } 
}
