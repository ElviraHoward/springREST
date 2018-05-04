package com.springRest.demo.controller;

import com.springRest.demo.model.Film;
import com.springRest.demo.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/film")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Film> getAll(){
        return filmService.findAll();
    }
}
