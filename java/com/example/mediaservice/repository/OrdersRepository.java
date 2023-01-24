package com.example.mediaservice.repository;

import com.example.mediaservice.model.Films;
import com.example.mediaservice.model.Orders;
import com.example.mediaservice.model.Users;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends GenericRepository<Orders>{
    List<Orders> findOrdersByUsers(Users users);
    List<Orders> findOrdersByFilms(Films films);




}
