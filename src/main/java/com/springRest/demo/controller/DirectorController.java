package com.springRest.demo.controller;

import com.springRest.demo.model.Director;
import com.springRest.demo.model.Film;
import com.springRest.demo.service.DirectorService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/director")
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Director> getAll() {
        return directorService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Director findById(@PathVariable(value = "id") Optional<Long> id_director) {
        if (id_director.isPresent()) {
            return directorService.findById(id_director.get());
        }
        return null;
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(value = "id") Optional<Long> id_director) {
        if (id_director.isPresent()) {
            directorService.delete(id_director.get());
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void edit(@RequestBody Director director){
        directorService.edit(director);
    }

    @PostMapping
    public Director save(@RequestBody DirectorDto directorDto){
        Director director = new Director();
        director.setName(directorDto.name);
        director.setCountOfOscars(directorDto.countOfOscars);
        director.setFilmEntity(directorDto.filmByDirector);
        return directorService.save(director);
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
