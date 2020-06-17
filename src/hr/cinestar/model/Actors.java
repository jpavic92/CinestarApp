/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.cinestar.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Josip
 */
    public class Actors {
    private List<Person> actors;
    
    public Actors(){}
    
    public Actors(List<Person> actors){
        this.actors = actors;
    }

    @XmlElement(name = "actor")
    public List<Person> getActors() {
        return actors;
    } 
}
