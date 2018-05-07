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

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/director")
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<Director> getAll() throws TransformerException {
            StreamSource source = new StreamSource(new File("C:/project/demo/src/main/resources/director.xml"));
            StreamSource xslt = new StreamSource(new File("C:/project/demo/src/main/resources/director.xsl"));

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(xslt);

            StreamResult result = new StreamResult(new File("C:/project/demo/src/main/resources/director_output.html"));
            transformer.transform(source, result);

       return directorService.findAll();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Director findById(@PathVariable(value = "id") Optional<Long> id_director) {
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
}
