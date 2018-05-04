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

    public Film findById (Long id_film) {
        return filmRepository.getOne(id_film);
    }

    public Film edit(Film editedFilm) {
        Film film = filmRepository.getOne(editedFilm.getId());
        if (film != null) {
            film.setName(editedFilm.getName());
            film.setReleaseDate(editedFilm.getReleaseDate());
            film.setRating(editedFilm.getRating());
            film.setDirectorEntity(editedFilm.getDirectorEntity());
            film = filmRepository.save(film);
        }
        return film;
    }

    public List<Film> findAll() {
        List<Film> films = filmRepository.findAll();
        return films != null ? films : new ArrayList<>();
    }

    public void delete(Film film) {
        filmRepository.delete(film);
    }
}
