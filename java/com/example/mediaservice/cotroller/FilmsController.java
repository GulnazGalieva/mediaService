package com.example.mediaservice.cotroller;

import com.example.mediaservice.model.Films;
import com.example.mediaservice.repository.GenericRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/rest/films")
public class FilmsController extends GenericController<Films>{
    protected FilmsController(GenericRepository<Films> repository) {
        super(repository);
    }
}
