package com.example.mediaservice.dto;

import lombok.*;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DirectorsWithFilmsDto extends DirectorsDto{
    private Set<FilmsDto> films;
}
