package com.springRest.demo.controller.xsltSettings;

import com.springRest.demo.model.Film;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elvira on 03.06.2018.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "films")
public class Films {
    @XmlElement(name = "film", type = Film.class)
    private List<Film> films = new ArrayList<>();

    public Films() {
    }

    public Films(List<Film> films) {
        this.films = films;
    }

    public List<Film> getFilms() {
        return films;
    }
}
