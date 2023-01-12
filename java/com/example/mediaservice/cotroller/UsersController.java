package com.example.mediaservice.cotroller;

import com.example.mediaservice.model.Users;
import com.example.mediaservice.repository.GenericRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/rest/users")
public class UsersController extends GenericController<Users>{
    protected UsersController(GenericRepository<Users> repository) {
        super(repository);
    }
}
