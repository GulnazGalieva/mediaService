package com.example.mediaservice.cotroller;
import com.example.mediaservice.model.Users;
import com.example.mediaservice.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/rest/users")
public class UsersController extends GenericController<Users>{
    private final UsersService service;

    protected UsersController(UsersService service) {
        super(service);
        this.service = service;
    }

    @Operation(description = "Список всех арендованных/купленных фильмов у пользователя", method = "OrdersOfUsers")
    @GetMapping("/list/{id}")
    public ResponseEntity<?> ordersOfUsers(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body( service.ordersOfUsers(id));
    }


}
