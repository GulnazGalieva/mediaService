package com.example.mediaservice.mapper;

import com.example.mediaservice.dto.DirectorsDto;
import com.example.mediaservice.dto.FilmsDto;
import com.example.mediaservice.model.Directors;
import com.example.mediaservice.model.Films;
import com.example.mediaservice.model.GenericModel;
import com.example.mediaservice.repository.DirectorsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FilmsMapper extends GenericMapper<Films, FilmsDto> {
    private final ModelMapper mapper;
    private final DirectorsRepository directorsRepository;

    protected FilmsMapper(ModelMapper mapper,  DirectorsRepository directorsRepository) {
        super(mapper, Films.class, FilmsDto.class);
        this.mapper = mapper;
        this.directorsRepository = directorsRepository;
    }
    @PostConstruct//аннотация мапера
    //инструкция для мапера, что нужно сделать маперу при нахождении метода , например setFilmsId
    public void setupMapper(){
        mapper.createTypeMap(Films.class,FilmsDto.class)
                .addMappings(m->m.skip(FilmsDto::setDirectorsIds)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(FilmsDto.class, Films.class).addMappings(m -> m.skip(Films::setDirectors))
                .setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(FilmsDto source, Films destination) {
        if (!Objects.isNull(source.getDirectorsIds())) {
            destination.setDirectors(directorsRepository.findAllByIdIn(source.getDirectorsIds()));
        } else {
            destination.setDirectors(null);
        }
    }

    @Override
    void mapSpecificFields(Films source,FilmsDto destination) {
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
