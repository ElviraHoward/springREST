package com.springRest.demo.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "director")
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
}
