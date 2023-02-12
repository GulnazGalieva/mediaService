package com.example.mediaservice.cotroller;
import com.example.mediaservice.dto.LoginDto;
import com.example.mediaservice.dto.UserDto;
import com.example.mediaservice.mapper.UsersMapper;
import com.example.mediaservice.model.Users;
import com.example.mediaservice.security.JwtTokenUtil;
import com.example.mediaservice.service.UsersService;
import com.example.mediaservice.service.userDetails.CustomUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/rest/users")
public class UsersController extends GenericController<Users, UserDto>{
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService customUserDetailsService;

    private final UsersService service;

    protected UsersController(UsersService service, UsersMapper mapper, JwtTokenUtil jwtTokenUtil, CustomUserDetailsService customUserDetailsService) {
        super(service, mapper);
        this.service = service;
        this.jwtTokenUtil = jwtTokenUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Operation(description = "Список всех арендованных/купленных фильмов у пользователя", method = "OrdersOfUsers")
    @GetMapping("/list/{id}")
    public ResponseEntity<?> ordersOfUsers(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body( service.ordersOfUsers(id));
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody LoginDto loginDto) {
        Map<String, Object> response = new HashMap<>();

        if(!service.checkPassword(loginDto)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized user!\nWrongPassword");
        }
        UserDetails foundUser = customUserDetailsService.loadUserByUsername(loginDto.getLogin());
        String token = jwtTokenUtil.generateToken(foundUser);
        response.put("token", token);
        response.put("authorities", foundUser.getAuthorities());
        return ResponseEntity.ok().body(response);

    }



}
