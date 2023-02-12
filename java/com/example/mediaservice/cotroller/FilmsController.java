package com.example.mediaservice.cotroller;

import com.example.mediaservice.dto.FilmsDto;
import com.example.mediaservice.mapper.FilmsMapper;
import com.example.mediaservice.model.Films;
import com.example.mediaservice.model.Genre;
import com.example.mediaservice.service.FilmsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/rest/films")
public class FilmsController extends GenericController<Films, FilmsDto>{

    private final FilmsService service;

    protected FilmsController(FilmsService service, FilmsMapper mapper) {
        super(service, mapper);
        this.service = service;

    }
    @Operation(description = "поиск фильма в сервисе по названию/стране/жанру", method = "search")
    @GetMapping("/search")
    public List<Films> search(
            @RequestParam(value = "title",required = false)String title,
            @RequestParam(value = "genre", required = false) Genre genre,
            @RequestParam(value = "country", required = false) String country
    ){
        return service.search(
                title,genre, country);
    }
//    @Operation(description = "Добавить участников к фильму", method = "addDirectors")// то что будет выводится в swagger
//    @PutMapping("/add")
//    public ResponseEntity<?> addDirectors(
//            @RequestParam(value = "id участника",required = true)Long id,
//            @RequestParam(value = "id фильма",required = true)Long ids){
//
//        return ResponseEntity.status(HttpStatus.OK).body(service.update(service.addDirectors(id,ids)));
//    }


}
