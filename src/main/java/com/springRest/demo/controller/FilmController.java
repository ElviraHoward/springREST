package com.springRest.demo.controller;

import com.springRest.demo.model.Director;
import com.springRest.demo.model.Film;
import com.springRest.demo.service.FilmService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/film")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Film> getAll(){
        return filmService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Film findById(@PathVariable(value = "id") Optional<Long> id_film){
        if (id_film.isPresent()) {
            return filmService.findById(id_film.get());
        }
        return null;
    }

    @DeleteMapping(value = "/{id}")
    public void  delete(@PathVariable(value = "id") Optional<Long> id_film){
        if (id_film.isPresent()) {
            filmService.delete(id_film.get());
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void edit(@RequestBody Film film){
        filmService.edit(film);
    }

    @PostMapping
    public Film save(@RequestBody FilmDto filmDto){
        Film film = new Film();
        film.setName(filmDto.name);
        film.setReleaseDate(filmDto.releaseDate);
        film.setRating(filmDto.rating);
        film.setDirectorByFilm(filmDto.directorByFilm);
        return filmService.save(film);
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
}
