package com.springRest.demo.controller;

import com.springRest.demo.model.Director;
import com.springRest.demo.model.Film;
import com.springRest.demo.service.FilmService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/film")
public class FilmController {

    @Autowired
    private FilmService filmService;

    private static final String HTTP_GET_FILMS = "http://localhost:8080/film/";

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<Film> getAll() throws TransformerException, ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL(HTTP_GET_FILMS).openStream());
        Source source = new DOMSource(doc);
        StreamSource xslt = new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream("xslt/film.xsl"));
        generatePage(source, xslt);
        return filmService.findAll();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Film findById(@PathVariable(value = "id") Optional<Long> id_film)  throws TransformerException, ParserConfigurationException, IOException, SAXException{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL(HTTP_GET_FILMS + id_film).openStream());
        Source source = new DOMSource(doc);
        StreamSource xslt = new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream("xslt/film.xsl"));
        generatePage(source, xslt);
        if (id_film.isPresent()) {
            return filmService.getById(id_film.get());
        }
        return null;
    }

    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void  delete(@PathVariable(value = "id") Optional<Long> id_film){
        if (id_film.isPresent()) {
            filmService.delete(id_film.get());
        }
    }

    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity edit(@PathVariable("id") Optional<Long> id_film, FilmDto filmDto){
        if (filmDto != null && id_film.isPresent()) {
            Film film = filmService.getById(id_film.get());
            film.setName(filmDto.name);
            film.setRating(filmDto.rating);
            film.setReleaseDate(filmDto.releaseDate);
            film.setDirectorByFilm(filmDto.directorByFilm);
            film = filmService.save(film);
            return new ResponseEntity(film, HttpStatus.OK);
        }
        return new ResponseEntity(new Object(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity save(FilmDto filmDto){
        if (filmDto != null) {
            Film film = new Film();
            film.setName(filmDto.name);
            film.setRating(filmDto.rating);
            film.setReleaseDate(filmDto.releaseDate);
            film.setDirectorByFilm(filmDto.directorByFilm);
            film = filmService.save(film);
            return new ResponseEntity(film, HttpStatus.OK);
        }
        return new ResponseEntity(new Object(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Getter
    @Setter
    private static class FilmDto{
        private String name;
        private int releaseDate;
        private double rating;
        private Director directorByFilm;

        public FilmDto() {
        }

        public FilmDto(String name, int releaseDate, double rating, Director directorByFilm) {
            this.name = name;
            this.releaseDate = releaseDate;
            this.rating = rating;
            this.directorByFilm = directorByFilm;
        }
    }

    private ResponseEntity.BodyBuilder generatePage(Source xml, Source xslt) {
        StringWriter sw = new StringWriter();
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer trasform = tFactory.newTransformer(xslt);
            trasform.transform(xml, new StreamResult(sw));
        } catch (TransformerFactoryConfigurationError | TransformerException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok();
    }
}
