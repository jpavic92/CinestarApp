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
@XmlRootElement (name = "genres")
public class Genres {
     private List<Genre> genres;
    
    public Genres(){}
    
    public Genres(List<Genre> genres){
        this.genres = genres;
    }

    @XmlElement(name = "genre")
    public List<Genre> getGenres() {
        return genres;
    }   
}
