package com.example.mediaservice.cotroller;

import com.example.mediaservice.model.Orders;
import com.example.mediaservice.repository.GenericRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/rest/orders")
public class OrdersController extends GenericController<Orders> {
    protected OrdersController(GenericRepository<Orders> repository) {
        super(repository);
    }
}
