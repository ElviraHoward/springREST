package com.springRest.demo.service;

import com.springRest.demo.model.Director;
import com.springRest.demo.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectorService {

    @Autowired
    private DirectorRepository directorRepository;

    public Director save(Director director) {
        return directorRepository.save(director);
    }

    public Director findById (Long id_director) {
        return directorRepository.getOne(id_director);
    }

    public Director edit(Director editedDirector) {
        Director director = directorRepository.getOne(editedDirector.getId());
        if (director != null) {
            director.setName(editedDirector.getName());
            director.setCountOfOscars(editedDirector.getCountOfOscars());
            director.setFilmEntity(editedDirector.getFilmEntity());
            director = directorRepository.save(director);
        }
        return director;
    }

    public List<Director> findAll() {
        List<Director> directors = directorRepository.findAll();
        return directors != null ? directors : new ArrayList<>();
    }

    public void delete(Director director) {
        directorRepository.delete(director);
    }
}
