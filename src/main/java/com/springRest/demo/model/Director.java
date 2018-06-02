package com.springRest.demo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "director")
@XmlRootElement
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "directorByFilm")
    private Collection<Film> filmEntity;

    public Director() {
    }

    @Override
    public String toString() {
        return "DirectorEntity{" +
                "name='" + name + '\'' +
                ", countOfOscars=" + countOfOscars +
                ", film=" + filmEntity +
                '}';
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

    public Collection<Film> getFilmEntity() {
        return filmEntity;
    }

    public void setFilmEntity(Collection<Film> filmEntity) {
        this.filmEntity = filmEntity;
    }
}
