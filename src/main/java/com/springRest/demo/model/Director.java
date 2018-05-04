package com.springRest.demo.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "director")
public class Director implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "count_of_oscars")
    private int countOfOscars;

    @OneToMany
    @JoinColumn(name = "id_film")
    private Film filmEntity;

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
