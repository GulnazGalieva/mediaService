package com.example.mediaservice.mapper;

import com.example.mediaservice.dto.DirectorsDto;
import com.example.mediaservice.dto.DirectorsWithFilmsDto;
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
public class DirectorsWithFilmsMapper extends GenericMapper<Directors, DirectorsWithFilmsDto>{
    private ModelMapper mapper;
    private  final FilmsRepository filmsRepository;

    protected DirectorsWithFilmsMapper(ModelMapper mapper,  FilmsRepository filmsRepository) {
        super(mapper, Directors.class, DirectorsWithFilmsDto.class );
        this.mapper = mapper;
        this.filmsRepository = filmsRepository;
    }
    @PostConstruct//аннотация мапера
    //инструкция для мапера, что нужно сделать маперу при нахождении метода , например setFilmsId
    public void setupMapper(){
        mapper.createTypeMap(Directors.class, DirectorsWithFilmsDto.class)
                .addMappings(m->m.skip(DirectorsWithFilmsDto::setFilmsIds)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(DirectorsWithFilmsDto.class, Directors.class)
                .addMappings(m -> m.skip(Directors::setFilms)).setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(DirectorsWithFilmsDto source, Directors destination) {
//        if (!Objects.isNull(source.getFilms())) {
            destination.setFilms(filmsRepository.findAllByIdIn(source.getFilmsIds()));
//        } else {
//            destination.setFilms(null);
//        }
    }

    @Override
    void mapSpecificFields(Directors source, DirectorsWithFilmsDto destination) {
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
