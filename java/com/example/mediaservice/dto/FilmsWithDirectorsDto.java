package com.example.mediaservice.dto;

import lombok.*;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class FilmsWithDirectorsDto extends FilmsDto{
    private Set<DirectorsDto> directors;
}
