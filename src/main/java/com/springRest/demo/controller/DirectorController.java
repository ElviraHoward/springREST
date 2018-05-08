package com.springRest.demo.controller;

import com.springRest.demo.model.Director;
import com.springRest.demo.model.Film;
import com.springRest.demo.service.DirectorService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/director")
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    private static final String HTTP_GET_DIRECTORS = "http://localhost:8080/film/";

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<Director> getAll() throws TransformerException, ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL(HTTP_GET_DIRECTORS).openStream());
        Source source = new DOMSource(doc);
        StreamSource xslt = new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream("xslt/director.xsl"));
        generatePage(source, xslt);

       return directorService.findAll();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Director findById(@PathVariable(value = "id") Optional<Long> id_director) throws TransformerException, ParserConfigurationException, IOException, SAXException{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL(HTTP_GET_DIRECTORS + id_director).openStream());
        Source source = new DOMSource(doc);
        StreamSource xslt = new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream("xslt/director.xsl"));
        generatePage(source, xslt);
        if (id_director.isPresent()) {
            return directorService.getById(id_director.get());
        }
        return null;
    }

    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void delete(@PathVariable(value = "id") Optional<Long> id_director) {
        if (id_director.isPresent()) {
            directorService.delete(id_director.get());
        }
    }

    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity edit(@PathVariable("id") Optional<Long> id_director, DirectorDto directorDto){
        if (directorDto != null && id_director.isPresent()) {
            Director director = directorService.getById(id_director.get());
            director.setName(directorDto.name);
            director.setCountOfOscars(directorDto.countOfOscars);
            director.setFilmEntity(directorDto.filmByDirector);
            director = directorService.save(director);
            return new ResponseEntity(director, HttpStatus.OK);
        }
        return new ResponseEntity(new Object(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity save(DirectorDto directorDto){
        if (directorDto != null) {
            Director director = new Director();
            director.setName(directorDto.name);
            director.setCountOfOscars(directorDto.countOfOscars);
            director.setFilmEntity(directorDto.filmByDirector);
            director = directorService.save(director);
            return new ResponseEntity(director, HttpStatus.OK);
        }
        return new ResponseEntity(new Object(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Getter
    @Setter
    private static class DirectorDto{
        private String name;
        private int countOfOscars;
        private Collection<Film> filmByDirector;

        public DirectorDto() {
        }

        public DirectorDto(String name, Integer countOfOscars, Collection<Film> filmByDirector) {
            this.name = name;
            this.countOfOscars = countOfOscars;
            this.filmByDirector = filmByDirector;
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
