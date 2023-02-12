package com.example.mediaservice.dto;

import com.example.mediaservice.model.Films;
import com.example.mediaservice.model.Users;
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
public class OrdersDto extends GenericDto{

    private LocalDateTime rentDate;
    private LocalDateTime rentPeriod;
    private boolean purchase;
    private Long usersId;
    private Long filmsId;

}
