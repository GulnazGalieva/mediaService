package com.example.mediaservice.service;

import com.example.mediaservice.dto.RentFilmDto;
import com.example.mediaservice.model.Films;
import com.example.mediaservice.model.Orders;
import com.example.mediaservice.model.Users;
import com.example.mediaservice.repository.GenericRepository;
import com.example.mediaservice.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdersService extends GenericService<Orders>{
    private final OrdersRepository ordersRepository;
    private final UsersService usersService;
    private final FilmsService filmsService;
    protected OrdersService( OrdersRepository ordersRepository, UsersService usersService, FilmsService filmsService) {
        super(ordersRepository);
        this.ordersRepository = ordersRepository;
        this.usersService = usersService;
        this.filmsService = filmsService;
    }
    public Orders rentAFilm(RentFilmDto rentFilmDto){
        Users users = usersService.getOne(rentFilmDto.getUsersId());
        Films films = filmsService.getOne(rentFilmDto.getFilmsId());
        Orders orders = Orders.builder()
                .rentDate(LocalDateTime.now())
                .rentPeriod(LocalDateTime.now().plusDays(rentFilmDto.getRentPeriod()))
                .users(users)
                .films(films)
                .build();
        return ordersRepository.save(orders);
    }

    public List<Orders> getOrdersOfUsers(Long userId){
        return ordersRepository.findOrdersByUsers(usersService.getOne(userId));
    }

}
