package com.springRest.demo.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Entity
@Table(name = "film")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "release_date")
    private int releaseDate;

    @Column(name = "rating")
    private double rating;

    @ManyToOne
    @JoinColumn(name = "id_director")
    private Director directorEntity;

    public Film() {
    }

    @Override
    public String toString() {
        return "FilmEntity{" +
                "name='" + name + '\'' +
                ", releaseDate=" + releaseDate +
                ", rating=" + rating +
                ", director=" + directorEntity +
                '}';
    }
}
