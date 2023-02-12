package com.example.mediaservice.mapper;

import com.example.mediaservice.dto.DirectorsDto;
import com.example.mediaservice.model.Directors;
import com.example.mediaservice.model.GenericModel;
import com.example.mediaservice.repository.FilmsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DirectorsMapper extends GenericMapper<Directors, DirectorsDto>{
    private final ModelMapper mapper;
    private final FilmsRepository filmsRepository;

    public DirectorsMapper(ModelMapper mapper, FilmsRepository filmsRepository) {
        super(mapper, Directors.class, DirectorsDto.class);
        this.mapper = mapper;
        this.filmsRepository = filmsRepository;
    }
    @PostConstruct//аннотация мапера
    //инструкция для мапера, что нужно сделать маперу при нахождении метода , например setFilmsId
    public void setupMapper(){
        mapper.createTypeMap(Directors.class, DirectorsDto.class)
                .addMappings(m->m.skip(DirectorsDto::setFilmsIds)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(DirectorsDto.class, Directors.class)
                .addMappings(m->m.skip(Directors::setFilms)).setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(DirectorsDto source, Directors destination) {
        if (!Objects.isNull(source.getFilmsIds())) {
            destination.setFilms(filmsRepository.findAllByIdIn(source.getFilmsIds()));
        } else {
            destination.setFilms(null);
        }
    }

    @Override
    void mapSpecificFields(Directors source, DirectorsDto destination) {
        destination.setFilmsIds(getIds(source));
    }
    private Set<Long> getIds(Directors directors){
        return Objects.isNull(directors) || Objects.isNull(directors.getId())
                ? null
                : directors.getFilms().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}
