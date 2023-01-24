package com.example.mediaservice.service;

import com.example.mediaservice.model.Orders;
import com.example.mediaservice.repository.GenericRepository;
import com.example.mediaservice.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class OrdersService extends GenericService<Orders>{
    private final OrdersRepository ordersRepository;
    protected OrdersService(GenericRepository<Orders> repository, OrdersRepository ordersRepository) {
        super(repository);
        this.ordersRepository = ordersRepository;
    }
    //Взять фильм в аренду/купить
    public Orders createRentFilm ( Orders orders){
        orders.setRentPeriod(LocalDateTime.now().plus(90, ChronoUnit.DAYS));
        orders.setPurchase(false);
        return ordersRepository.save(orders);
    }public Orders createPurchaseFilm ( Orders orders){
        return ordersRepository.save(orders);
    }

}
