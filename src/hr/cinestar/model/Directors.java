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
public class Directors {
    private List<Person> directors;
    
    public Directors(){}
    
    public Directors(List<Person> directors){
        this.directors = directors;
    }

    @XmlElement(name = "director")
    public List<Person> getDirectors() {
        return directors;
    } 
}
