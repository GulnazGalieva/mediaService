package com.example.mediaservice.mapper;

import com.example.mediaservice.dto.FilmsWithDirectorsDto;
import com.example.mediaservice.model.Films;
import com.example.mediaservice.model.GenericModel;
import com.example.mediaservice.repository.DirectorsRepository;
import com.example.mediaservice.repository.FilmsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FilmsWithDirectorsMapper extends GenericMapper<Films, FilmsWithDirectorsDto> {
    private final ModelMapper mapper;
    private final DirectorsRepository directorsRepository;
    private final FilmsRepository filmsRepository;

    protected FilmsWithDirectorsMapper(ModelMapper mapper, DirectorsRepository directorsRepository,
                                       FilmsRepository filmsRepository) {
        super(mapper, Films.class, FilmsWithDirectorsDto.class);
        this.mapper = mapper;
        this.directorsRepository = directorsRepository;
        this.filmsRepository = filmsRepository;
    }
    @PostConstruct// анотация мапера
    public void setupMapper(){
        mapper.createTypeMap(Films.class, FilmsWithDirectorsDto.class)
                .addMappings(m->m.skip(FilmsWithDirectorsDto::setDirectorsIds)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(FilmsWithDirectorsDto.class, Films.class).
                addMappings(m -> m.skip(Films::setDirectors)).setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(FilmsWithDirectorsDto source, Films destination) {
        destination.setDirectors(directorsRepository.findAllByIdIn(source.getDirectorsIds()));
    }

    @Override
    void mapSpecificFields(Films source, FilmsWithDirectorsDto destination) {
        destination.setDirectorsIds(getIds(source));
    }
    private Set<Long> getIds(Films films){
        return Objects.isNull(films) || Objects.isNull(films.getId())
                ? null
                : films.getDirectors().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}
