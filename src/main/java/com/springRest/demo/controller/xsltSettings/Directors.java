package com.springRest.demo.controller.xsltSettings;

import com.springRest.demo.model.Director;

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
@XmlRootElement(name = "directors")
public class Directors {

    @XmlElement(name = "director", type = Director.class)
    private List<Director> directors = new ArrayList<>();

    public Directors() {
    }

    public Directors(List<Director> directors) {
        this.directors = directors;
    }

    public List<Director> getDirectors() {
        return directors;
    }
}
