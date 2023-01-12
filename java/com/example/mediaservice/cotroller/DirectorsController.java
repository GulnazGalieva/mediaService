package com.example.mediaservice.cotroller;

import com.example.mediaservice.model.Directors;
import com.example.mediaservice.repository.GenericRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/rest/directors")
public class DirectorsController extends GenericController<Directors> {
    protected DirectorsController(GenericRepository<Directors> repository) {
        super(repository);
    }
}
