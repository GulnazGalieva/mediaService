package com.example.mediaservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass // позволяет, чтобы у унаследованных классов появился данный модель
public abstract class GenericModel {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_generator")
    private Long id;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_when")
    private LocalDateTime createdWhen;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "updated_when")
    private LocalDateTime updatedWhen;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Column(name = "deleted_by")
    private String deletedBy;
    @Column(name = "deleted_when")
    private LocalDateTime deletedWhen;
}
