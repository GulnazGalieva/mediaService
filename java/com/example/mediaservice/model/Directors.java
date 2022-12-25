package com.example.mediaservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "directors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Directors {
    @Id
    @Setter(AccessLevel.NONE)
    @Column (name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "directors_fio")
    String directorsFIO;

    @Column(name = "position")
    String position;
    @ManyToMany(mappedBy = "directors",fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private Set<Films> films = new HashSet<>();

}
