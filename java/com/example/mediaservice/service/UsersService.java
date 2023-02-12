package com.example.mediaservice.service;

import com.example.mediaservice.dto.LoginDto;
import com.example.mediaservice.model.Orders;
import com.example.mediaservice.model.Users;
import com.example.mediaservice.repository.GenericRepository;
import com.example.mediaservice.repository.OrdersRepository;
import com.example.mediaservice.repository.RoleRepository;
import com.example.mediaservice.repository.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UsersService extends GenericService<Users>{

    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;
    private final RoleRepository roleRepository;


    protected UsersService( UsersRepository usersRepository,
                           OrdersRepository ordersRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           RoleService roleService,
                            RoleRepository roleRepository) {
        super( usersRepository);
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }

    @Override
    public Users create(Users user) {
        user.setCreatedBy("REGISTRATION");
        user.setRole(roleService.getOne(1L));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }
    public Users createDirectors(Users user) {
        user.setCreatedBy("ADMIN");
        user.setRole(roleService.getOne(2L));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    //Список всех арендованных/купленных фильмов у пользователя
    public List<Orders> ordersOfUsers(Long id){
        return ordersRepository.findOrdersByUsers(usersRepository.findById(id).orElseThrow());
    }

    public Users getByLogin(String login) {
        return usersRepository.findUsersByLogin(login);
    }
// проверка на валидность логина и пароля юзера
    public boolean checkPassword(LoginDto loginDto) {
        return bCryptPasswordEncoder.matches(loginDto.getPassword(), getByLogin(loginDto.getLogin()).getPassword());
    }



}
