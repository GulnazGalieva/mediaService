package com.example.mediaservice.cotroller;

import com.example.mediaservice.model.Directors;
import com.example.mediaservice.service.DirectorsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/rest/directors")
public class DirectorsController extends GenericController<Directors> {
    private final DirectorsService service;

    protected DirectorsController(DirectorsService service) {
        super(service);
        this.service = service;
    }


//    User user = session.load(User.class, 50);
//if (user != null) {
//        session.delete(user);
//    }
}
