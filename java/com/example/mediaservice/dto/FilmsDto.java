package com.example.mediaservice.dto;

import com.example.mediaservice.model.Genre;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilmsDto  extends GenericDto{

    private String title;
    private String premierYear;
    private String country;
    private Genre genre;
    private Set<Long> directorsIds;

}
