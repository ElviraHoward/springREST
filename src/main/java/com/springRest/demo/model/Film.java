package com.springRest.demo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name = "film")
@XmlRootElement
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

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Director getDirectorByFilm() {
        return directorByFilm;
    }

    public void setDirectorByFilm(Director directorByFilm) {
        this.directorByFilm = directorByFilm;
    }
}
