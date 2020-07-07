/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.cinestar.model;

import java.util.Objects;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Josip
 */
@XmlRootElement (name = "genre")
public class Genre {
    private int id;
    private String name;
    
    public Genre(){}

    public Genre(int id, String name) {
        this(name);
        this.id = id;
    }

    public Genre(String name) {
        this.name = name;
    }

    @XmlAttribute (name = "id")
    public int getId() {
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }

    @XmlElement (name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name.toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Genre) {
            return name.toLowerCase().equals(((Genre) o).name.toLowerCase());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    
    
    
    
}
