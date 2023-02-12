package com.example.mediaservice.cotroller;

import com.example.mediaservice.dto.AddFilmDto;
import com.example.mediaservice.dto.DirectorsDto;
import com.example.mediaservice.mapper.DirectorsMapper;
import com.example.mediaservice.mapper.DirectorsWithFilmsMapper;
import com.example.mediaservice.model.Directors;
import com.example.mediaservice.service.DirectorsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/rest/directors")
public class DirectorsController extends GenericController<Directors, DirectorsDto> {
    private final DirectorsService service;
    private final DirectorsWithFilmsMapper directorsWithFilmsMapper;

    protected DirectorsController(DirectorsService service, DirectorsMapper mapper, DirectorsWithFilmsMapper directorsWithFilmsMapper) {
        super(service, mapper);
        this.service = service;
        this.directorsWithFilmsMapper = directorsWithFilmsMapper;
    }
    @PostMapping("/add-film")
    public DirectorsDto addFilm(@RequestBody AddFilmDto addFilmDto){
        return directorsWithFilmsMapper.toDto(service.addFilm(addFilmDto));
    }


//    User user = session.load(User.class, 50);
//if (user != null) {
//        session.delete(user);
//    }
}
