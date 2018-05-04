package com.springRest.demo.controller;

import com.springRest.demo.model.Director;
import com.springRest.demo.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/director")
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Director> getAll(){
        return directorService.findAll();
    }

}
