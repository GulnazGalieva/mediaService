package com.example.mediaservice.cotroller;

import com.example.mediaservice.model.Orders;
import com.example.mediaservice.service.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/rest/orders")
public class OrdersController extends GenericController<Orders> {
    private final OrdersService service;
    protected OrdersController(OrdersService service) {
        super(service);
        this.service = service;
    }
    @Operation(description = "Взять фильм в аренду", method = "createRentFilm")
    @PostMapping("/rentFilm")
    public ResponseEntity<?> createRentFilm (@RequestBody Orders orders){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createRentFilm(orders));
    }

    @Operation(description = "Купить фильм", method = "createPurchaseFilm")
    @PostMapping ("/purchaseFilm")
    public ResponseEntity<?> createPurchaseFilm (@RequestBody Orders orders){
        return ResponseEntity.status(HttpStatus.OK).body(service.createPurchaseFilm(orders));
    }

}
