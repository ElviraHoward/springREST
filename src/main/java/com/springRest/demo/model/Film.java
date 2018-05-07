package com.springRest.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "film")
public class Film implements Serializable {

    @Id
    @GenericGenerator(name = "num", strategy = "increment")
    @GeneratedValue(generator = "num")
    @Column(name = "id_film")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "release_date")
    private int releaseDate;

    @Column(name = "rating")
    private double rating;

    @ManyToOne(cascade= {CascadeType.REFRESH}, fetch=FetchType.LAZY)
    @JoinColumn(name = "director", referencedColumnName = "id_director")
    private Director directorByFilm;

    public Film() {
    }

    @Override
    public String toString() {
        return "FilmEntity{" +
                "name='" + name + '\'' +
                ", releaseDate=" + releaseDate +
                ", rating=" + rating +
                ", director=" + directorByFilm +
                '}';
    }
}
