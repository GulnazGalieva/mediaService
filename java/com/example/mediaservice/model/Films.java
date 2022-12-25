package com.example.mediaservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "films")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Films {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "title")
    String title;

    @Column(name = "premier_year")
    String premier_year;

    @Column(name = "country")
    String country;

    @Column(name = "genre")
    Genre genre;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "film_directors",
            joinColumns = @JoinColumn(name = "films_id"),
            foreignKey = @ForeignKey(name = "FK_FILMS_DIRECTORS"),
            inverseJoinColumns = @JoinColumn (name = "directors_id"),
            inverseForeignKey = @ForeignKey(name = "FK_DIRECTORS_FILMS")
    )
    private Set<Directors> directors = new HashSet<>();

}
