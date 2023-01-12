package com.example.mediaservice.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "directors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_generator", sequenceName = "directors_seq", allocationSize = 1)
public class Directors extends GenericModel {

    @Column(name = "directors_fio")
    String directorsFIO;



    @Column(name = "position")
    String position;

    @ManyToMany(mappedBy = "directors",fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private Set<Films> films = new HashSet<>();
    @Builder
    public Directors(Long id, String createdBy, LocalDateTime createdWhen, String updatedBy, LocalDateTime updatedWhen,
                     boolean isDeleted, String deletedBy, LocalDateTime deletedWhen, String directorsFIO,
                     String position, Set<Films> films) {
        super(id, createdBy, createdWhen, updatedBy, updatedWhen, isDeleted, deletedBy, deletedWhen);
        this.directorsFIO = directorsFIO;
        this.position = position;
        this.films = films;
    }

}
