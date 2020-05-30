/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.cinestar.model;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Josip
 */
public class Movie {
    private int id;
    private String title;
    private String description;
    private String originalTitle;
    private String posterPath;
    private String link;
    private Date beginningDate;
    private List<Person> directors;
    private List<Person> actors;
    private List<Genre> genres;
    
    //public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public Movie(){
        
    }
    
    public Movie(int id, String title, String description, String originalTitle, String posterPath, String link, Date beginningDate, List<Person> directors, List<Person> actors, List<Genre> genres) {
        this(title, description, originalTitle, posterPath, link, beginningDate, directors, actors, genres);
        this.id = id;
    }

    public Movie(String title, String description, String originalTitle, String posterPath, String link, Date beginningDate, List<Person> directors, List<Person> actors, List<Genre> genres) {
        this.title = title;
        this.beginningDate = beginningDate;
        this.description = description;
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.link = link;
        this.directors = directors;
        this.actors = actors;
        this.genres = genres;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getBeginningDate() {
        return beginningDate;
    }

    public void setBeginningDate(Date beginningDate) {
        this.beginningDate = beginningDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genre) {
        this.genres = genre;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<Person> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Person> directors) {
        this.directors = directors;
    }

    public List<Person> getActors() {
        return actors;
    }

    public void setActors(List<Person> actors) {
        this.actors = actors;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Movie) {
            return title.equals(((Movie) o).title);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
    
    
    
    
    
    
}
