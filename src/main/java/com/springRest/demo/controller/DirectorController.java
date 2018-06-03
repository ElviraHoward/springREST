package com.springRest.demo.controller;

import com.springRest.demo.model.Director;
import com.springRest.demo.model.Film;
import com.springRest.demo.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/director")
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<Director> getAll() {
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
            director = directorService.save(director);
            return new ResponseEntity(director, HttpStatus.OK);
        }
        return new ResponseEntity(new Object(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static class DirectorDto{
        private String name = "Director";
        private int countOfOscars = 5;
        public DirectorDto() {
        }

        public DirectorDto(String name, Integer countOfOscars) {
            this.name = name;
            this.countOfOscars = countOfOscars;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCountOfOscars() {
            return countOfOscars;
        }

        public void setCountOfOscars(int countOfOscars) {
            this.countOfOscars = countOfOscars;
        }
    }
}
