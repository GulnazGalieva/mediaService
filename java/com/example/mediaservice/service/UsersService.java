package com.example.mediaservice.service;

import com.example.mediaservice.model.Orders;
import com.example.mediaservice.model.Users;
import com.example.mediaservice.repository.GenericRepository;
import com.example.mediaservice.repository.OrdersRepository;
import com.example.mediaservice.repository.UsersRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UsersService extends GenericService<Users>{

    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;

    protected UsersService(GenericRepository<Users> repository, UsersRepository usersRepository,
                           OrdersRepository ordersRepository) {
        super(repository);
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;

    }
    //Список всех арендованных/купленных фильмов у пользователя
    public List<Orders> ordersOfUsers(Long id){
        return ordersRepository.findOrdersByUsers(usersRepository.findById(id).orElseThrow());
    }


}
