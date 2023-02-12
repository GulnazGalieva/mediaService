package com.example.mediaservice.MVC.controller;

import com.example.mediaservice.dto.UserDto;
import com.example.mediaservice.mapper.UsersMapper;
import com.example.mediaservice.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class MVCUsersController {
    private final UsersService service;
    private final UsersMapper mapper;

    public MVCUsersController(UsersService service, UsersMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm")UserDto userDto){
        service.create(mapper.toEntity(userDto));
        return "redirect:login";

    }
}
