package com.example.mediaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass // позволяет, чтобы у унаследованных классов появился данный модель
public abstract class GenericDto {
    private Long id;
    private String createdBy;
    private LocalDateTime createdWhen;
    private String updatedBy;
    private LocalDateTime updatedWhen;
    private boolean isDeleted;
    private String deletedBy;
    private LocalDateTime deletedWhen;
}
