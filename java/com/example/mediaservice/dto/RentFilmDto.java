package com.example.mediaservice.dto;

import lombok.Data;

@Data
public class RentFilmDto {
    private Long filmsId;
    private Long usersId;
    Integer rentPeriod;
}
