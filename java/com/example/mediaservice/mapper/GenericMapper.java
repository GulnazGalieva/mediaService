package com.example.mediaservice.mapper;

import com.example.mediaservice.dto.GenericDto;
import com.example.mediaservice.model.GenericModel;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Objects;

public abstract class GenericMapper<E extends GenericModel, D extends GenericDto> implements Mapper<E,D> {
    protected final ModelMapper mapper;
    private final Class<E> entityClass;
    private final Class<D> dtoClass;

    protected GenericMapper(ModelMapper mapper, Class<E> entityClass, Class<D> dtoClass) {
        this.mapper = mapper;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }
    @Override
    public List<E> toEntities(List<D> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }

    @Override
    public E toEntity(D dto) {
        return Objects.isNull(dto)
                ? null
                :mapper.map(dto, entityClass);
    }

    @Override
    public D toDto(E entity) {
        return Objects.isNull(entity)
                ? null
                :mapper.map(entity, dtoClass);
    }

    @Override
    public List<D> toDtos(List<E> entities) {
        return entities.stream().map(this::toDto).toList();
    }

    Converter<D,E> toEntityConverter(){
        return context -> {
            D source = context.getSource();
            E destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    Converter<E,D> toDtoConverter(){
        return context -> {
            E source = context.getSource();
            D destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    void mapSpecificFields(D source, E destination){

    }
    void mapSpecificFields(E source, D destination){

    }

}
