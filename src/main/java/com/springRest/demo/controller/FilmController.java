package com.springRest.demo.controller;

import com.springRest.demo.model.Director;
import com.springRest.demo.model.Film;
import com.springRest.demo.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/film")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<Film> getAll() {
        return filmService.findAll();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Film findById(@PathVariable(value = "id") Optional<Long> id_film)  {
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(int releaseDate) {
            this.releaseDate = releaseDate;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public Director getDirectorByFilm() {
            return directorByFilm;
        }

        public void setDirectorByFilm(Director directorByFilm) {
            this.directorByFilm = directorByFilm;
        }
    }
}
