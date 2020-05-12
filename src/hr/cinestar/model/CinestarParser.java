/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.cinestar.model;

import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Josip
 */
public class CinestarParser {
    
    private static final String URL = "https://www.blitz-cinestar.hr/rss.aspx?najava=1";
    private static final String METHOD = "GET";
    private static final int TIMEOUT = 10000;
    
    private static final String DELIMITER = ",";
    private static final String DIR = "assets";
    private static final String EXT = "jpg";
    private static final Random RANDOM = new Random();
    
   
    
    

    //USPOSTAVITI URL CONNECTION
    //NAPRAVITI READER
    public static Set<Person> parsePersons() throws IOException, XMLStreamException {
        Set<Person> persons = new HashSet<>();
        XMLEventReader reader = getEventReader();

        //PARSIRATI
        StartElement startElement;
        Optional<TypeTag> typeTag = Optional.empty();
        
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            
            switch (event.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:
                    startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();
                    typeTag = TypeTag.from(qName);
                    break;
                
                case XMLStreamConstants.CHARACTERS:
                    
                    if (typeTag.isPresent() && (typeTag.get().equals(TypeTag.REDATELJ) || typeTag.get().equals(TypeTag.GLUMCI))) {
                        Characters asCharacters = event.asCharacters();
                        String data = asCharacters.getData().trim();
                        
                        if (!data.isEmpty()) {
                            addPersons(persons, data);
                        }
                    }
                    
                    break;
            }
        }
        return persons;
    }
    
    public static Set<Genre> parseGenres() throws IOException, XMLStreamException {
        Set<Genre> genres = new HashSet<>();
        XMLEventReader reader = getEventReader();
        
        StartElement startElement;
        String qName;
        Optional<TypeTag> typeTag = Optional.empty();
        
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            
            switch (event.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:
                    startElement = event.asStartElement();
                    qName = startElement.getName().getLocalPart();
                    typeTag = TypeTag.from(qName);
                    break;
                
                case XMLStreamConstants.CHARACTERS:
                    
                    if (typeTag.isPresent() && (typeTag.get().equals(TypeTag.ZANR))) {
                        Characters characters = event.asCharacters();
                        String data = characters.getData().trim();
                        
                        if (!data.isEmpty()) {
                            addGenres(genres, data);
                        }
                    }
                    
                    break;
            }
        }
        
        return genres;
    }
    
    public static List<Movie> parseMovies() throws IOException, XMLStreamException, ParseException {
        List<Movie> movies = new ArrayList<>();
        List<Person> directors;
        List<Person> actors;
        List<Genre> genres;
        XMLEventReader reader = getEventReader();
        
        Movie movie = null;
        StartElement startElement;
        String qName;
        Optional<TypeTag> typeTag = Optional.empty();
        
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            
            switch (event.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:
                    startElement = event.asStartElement();
                    qName = startElement.getName().getLocalPart();
                    typeTag = TypeTag.from(qName);
                    break;
                
                case XMLStreamConstants.CHARACTERS:
                    Characters characters = event.asCharacters();
                    String data = characters.getData().trim();
                    
                    if (typeTag.isPresent()) {
                        
                        switch (typeTag.get()) {
                            case ITEM:
                                movie = new Movie();
                                movies.add(movie);
                                break;
                            case TITLE:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setTitle(data);
                                }
                                break;
                            case DESCRIPTION:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setDescription(data.substring(data.indexOf(">") +1, data.lastIndexOf("<")));
                                }
                                break;
                            case ORIGNAZIV:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setOriginalTitle(data);
                                }
                                break;
                            case REDATELJ:
                                if (movie != null && !data.isEmpty()) {
                                    directors = new ArrayList<>();
                                    addPersons(directors, data);
                                    movie.setDirectors(directors);
                                }
                                break;
                            case GLUMCI:
                                if (movie != null && !data.isEmpty()) {
                                    actors = new ArrayList();
                                    addPersons(actors, data);
                                    movie.setActors(actors);
                                }
                                break;
                            case ZANR:
                                if (movie != null && !data.isEmpty()) {
                                    genres = new ArrayList<>();
                                    addGenres(genres, data);
                                    movie.setGenre(genres);
                                }
                                break;
                            case PLAKAT:
                                if (movie != null && !data.isEmpty()) {
                                    handleImage(movie, data);
                                    //movie.setPosterPath(data);
                                }
                                break;
                            case LINK:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setLink(data);
                                }
                                break;
                            case POCETAK:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setBeginningDate(Movie.DATE_FORMAT.parse(data));
                                }
                                break;
                        }
                    }
                    
                    break;
            }
            
        }
        return movies;
    }
    
    private static void addPersons(Collection<Person> persons, String data) {
        String[] string = data.split(DELIMITER);
        for (String person : string) {
            String[] name = person.trim().split(" ", 2);
            
            persons.add(new Person(name[0], name[1]));
        }
    }
    
    private static XMLEventReader getEventReader() throws IOException, XMLStreamException {
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(URL, TIMEOUT, METHOD);
        XMLEventReader reader = ParserFactory.createStaxParser(con.getInputStream());
        return reader;
    }
    
    private static void addGenres(Collection<Genre> genres, String data) {
        String[] string = data.split(DELIMITER);
        for (String genre : string) {
            genres.add(new Genre(genre.trim().toLowerCase()));
        }
    }

    private static void handleImage(Movie movie, String imageUrl) throws IOException {
        String ext = imageUrl.substring(imageUrl.lastIndexOf(".")+1);
        if (ext.length()> 4) {
            ext = EXT;
        }
        
        String imageName = Math.abs(RANDOM.nextInt()) + ext;
        String localImagePath = DIR + File.separator + imageName;
        
        FileUtils.copyContentFromUrl(imageUrl, localImagePath);
        movie.setPosterPath(localImagePath);
    }
       
    private enum TypeTag {
        ITEM("item"),
        TITLE("title"),
        DESCRIPTION("description"),
        ORIGNAZIV("orignaziv"),
        REDATELJ("redatelj"),
        GLUMCI("glumci"),
        ZANR("zanr"),
        PLAKAT("plakat"),
        LINK("link"),
        POCETAK("pocetak");
        
        private final String name;
        
        private TypeTag(String name) {
            this.name = name;
        }
        
        public static Optional<TypeTag> from(String tag) {
            for (TypeTag value : values()) {
                if (value.name.equals(tag.toLowerCase())) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
        
    }
}
