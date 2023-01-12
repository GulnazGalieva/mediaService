package com.example.mediaservice.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "films")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_generator", sequenceName = "films_seq", allocationSize = 1)
public class Films extends GenericModel{


    @Column(name = "title")
    String title;

    @Column(name = "premier_year")
    String premier_year;

    @Column(name = "country")
    String country;

    @Column(name = "genre")
    @Enumerated // необходимо поставить когда тип данных инам, чтобы все правильно создавалось
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

    @Builder
    public Films(Long id, String createdBy, LocalDateTime createdWhen, String updatedBy, LocalDateTime updatedWhen,
                 boolean isDeleted, String deletedBy,
                 LocalDateTime deletedWhen, String title, String premier_year, String country, Genre genre,
                 Set<Directors> directors) {
        super(id, createdBy, createdWhen, updatedBy, updatedWhen, isDeleted, deletedBy, deletedWhen);
        this.title = title;
        this.premier_year = premier_year;
        this.country = country;
        this.genre = genre;
        this.directors = directors;
    }
}
