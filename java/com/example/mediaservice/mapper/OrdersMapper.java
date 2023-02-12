package com.example.mediaservice.mapper;

import com.example.mediaservice.dto.OrdersDto;
import com.example.mediaservice.model.Orders;
import com.example.mediaservice.repository.FilmsRepository;
import com.example.mediaservice.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class OrdersMapper extends GenericMapper<Orders, OrdersDto>{
    private final ModelMapper mapper;
    private final FilmsRepository filmsRepository;
    private final UsersRepository usersRepository;

    protected OrdersMapper(ModelMapper mapper, FilmsRepository filmsRepository, UsersRepository usersRepository) {
        super(mapper, Orders.class, OrdersDto.class);
        this.mapper = mapper;
        this.filmsRepository = filmsRepository;
        this.usersRepository = usersRepository;
    }
    @PostConstruct// анотация мапера
    public void setupMapper(){
        super.mapper.createTypeMap(Orders.class, OrdersDto.class)
                .addMappings(m->m.skip(OrdersDto::setUsersId)).setPostConverter(toDtoConverter())
                .addMappings(m->m.skip(OrdersDto::setFilmsId)).setPostConverter(toDtoConverter());
        super.mapper.createTypeMap(OrdersDto.class, Orders.class)
                .addMappings(m->m.skip(Orders::setUsers)).setPostConverter(toEntityConverter())
                .addMappings(m->m.skip(Orders::setFilms)).setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(OrdersDto source, Orders destination) {
        destination.setFilms(filmsRepository.findById(source.getFilmsId()).orElseThrow());
        destination.setUsers(usersRepository.findById(source.getUsersId()).orElseThrow());
    }

    @Override
    void mapSpecificFields(Orders source, OrdersDto destination) {
        destination.setUsersId(source.getUsers().getId());
        destination.setFilmsId(source.getFilms().getId());
    }
}
