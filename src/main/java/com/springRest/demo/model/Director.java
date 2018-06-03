package com.springRest.demo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name = "director", schema = "spring")
@XmlRootElement(name = "director")
@XmlAccessorType(XmlAccessType.FIELD)
public class Director implements Serializable {

    @Id
    @GenericGenerator(name = "num", strategy = "increment")
    @GeneratedValue(generator = "num")
    @Column(name = "id_director")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "count_of_oscars")
    private int countOfOscars;

    public Director() {
    }

    @Override
    public String toString() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountOfOscars() {
        return countOfOscars;
    }

    public void setCountOfOscars(int countOfOscars) {
        this.countOfOscars = countOfOscars;
    }

}
