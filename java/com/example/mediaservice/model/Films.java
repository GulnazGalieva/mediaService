package com.example.mediaservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
   private String title;

    @Column(name = "premier_year")
    private String premierYear;

    @Column(name = "country")
    private String country;

    @Column(name = "genre")
    @Enumerated // необходимо поставить когда тип данных инам, чтобы все правильно создавалось
    private Genre genre;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
//    @JsonIgnore
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
        this.premierYear = premier_year;
        this.country = country;
        this.genre = genre;
        this.directors = directors;
    }
}
