package com.example.mediaservice.cotroller;

import com.example.mediaservice.dto.OrdersDto;
import com.example.mediaservice.dto.RentFilmDto;
import com.example.mediaservice.mapper.OrdersMapper;
import com.example.mediaservice.model.Orders;
import com.example.mediaservice.service.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/rest/orders")
public class OrdersController extends GenericController<Orders, OrdersDto> {
    private final OrdersService service;
    private final OrdersMapper mapper;
    protected OrdersController(OrdersService service, OrdersMapper mapper) {
        super(service, mapper);
        this.service = service;
        this.mapper = mapper;
    }
    @Operation(description = "Взять фильм в аренду", method = "rentAFilm")
    @PostMapping("/rentFilm")
    public OrdersDto rentAFilm (@RequestBody RentFilmDto rentFilmDto){
        return mapper.toDto(service.rentAFilm(rentFilmDto));
    }

    @Operation(description = "Список всех арендованных/купленных фильмов у пользователя", method = "getOrdersOfUsers")
    @GetMapping("user-orders/{userId}")
    public List<OrdersDto> getOrdersOfUsers(@PathVariable Long userId){
        return mapper.toDtos(service.getOrdersOfUsers(userId));
    }

}
