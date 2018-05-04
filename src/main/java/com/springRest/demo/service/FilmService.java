package com.springRest.demo.service;

import com.springRest.demo.model.Film;
import com.springRest.demo.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    public Film save(Film film) {
        return filmRepository.save(film);
    }

    public Film findById(Long id_film) {
        return filmRepository.findById(id_film).get();
    }

    public void edit(Film film) {
        Film prevFilm = filmRepository.findById(film.getId()).get();
        film.setName(prevFilm.getName());
        film.setReleaseDate(prevFilm.getReleaseDate());
        film.setRating(prevFilm.getRating());
        film.setDirectorByFilm(prevFilm.getDirectorByFilm());
        filmRepository.save(film);
    }

    public List<Film> findAll() {
        List<Film> films = filmRepository.findAll();
        return films != null ? films : new ArrayList<>();
    }

    public void delete(Long id_film) {
        filmRepository.deleteById(id_film);
    }
}
