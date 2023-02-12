package com.example.mediaservice.dto;

import com.example.mediaservice.model.Films;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DirectorsDto extends GenericDto{

    @NotBlank(message = "Поле не должно быть пустым")
    private String directorsFIO;
    @NotBlank(message = "Поле не должно быть пустым")
    private String position;

    private Set<Long> filmsIds;
}
