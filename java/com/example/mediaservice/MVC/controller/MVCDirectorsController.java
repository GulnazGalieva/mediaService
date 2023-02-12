package com.example.mediaservice.MVC.controller;

import com.example.mediaservice.dto.AddFilmDto;
import com.example.mediaservice.dto.DirectorsDto;
import com.example.mediaservice.dto.DirectorsWithFilmsDto;
import com.example.mediaservice.mapper.DirectorsMapper;
import com.example.mediaservice.mapper.DirectorsWithFilmsMapper;
import com.example.mediaservice.mapper.FilmsMapper;
import com.example.mediaservice.model.Directors;
import com.example.mediaservice.service.DirectorsService;
import com.example.mediaservice.service.FilmsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/directors")
public class MVCDirectorsController {
    private final DirectorsService service;
    private final FilmsService filmsService;
    private final DirectorsMapper mapper;
    private final DirectorsWithFilmsMapper directorsWithFilmsMapper;

    private final FilmsMapper filmsMapper;

    public MVCDirectorsController(DirectorsService service, FilmsService filmsService, DirectorsMapper mapper, DirectorsWithFilmsMapper directorsWithFilmsMapper, FilmsMapper filmsMapper) {
        this.service = service;
        this.filmsService = filmsService;
        this.mapper = mapper;
        this.directorsWithFilmsMapper = directorsWithFilmsMapper;
        this.filmsMapper = filmsMapper;
    }

    @GetMapping("")
    public String getAll(@RequestParam(value = "page",defaultValue = "1") int page,
                         @RequestParam(value = "size",defaultValue = "5") int pageSize,
                         Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "directorsFIO"));
        Page<Directors> directorsPage = service.listAllPaginated(pageRequest);
        List<DirectorsWithFilmsDto> directorsDtos = directorsPage
                .stream()
                .map(directorsWithFilmsMapper::toDto)
                .toList();
        model.addAttribute("directors", new PageImpl<>(directorsDtos, pageRequest, directorsPage.getTotalElements()));
        return "directors/viewAllDirectors";
    }
    @GetMapping("/add")
    public String create(@ModelAttribute("directorsForm") DirectorsDto directorsDto){
        return "directors/addDirectors";// путь до html файла
    }
//BindingResult позволяет пройти валидацию, проверяет все поля, для этого нужна анотация  @Valid
//    результат валидации напишется в переменную result
    @PostMapping("/add")
    public String create(@ModelAttribute("directorsForm") @Valid DirectorsDto directorsDto, BindingResult result) {
        if (result.hasErrors()) {
            return "/directors/addDirectors";
        } else {
            service.create(mapper.toEntity(directorsDto));
            return "redirect:/directors";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete (@PathVariable Long id){
        service.delete(id);
        return "redirect:/directors";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model){
        model.addAttribute("directors",directorsWithFilmsMapper.toDto(service.getOne(id)));
        return "directors/updateDirectors";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute("directorsForm")DirectorsDto directorsDto){
        service.update(mapper.toEntity(directorsDto));
        return "redirect:/directors";
    }

    @PostMapping("/search")
    public String searchByDirectorsFIO(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int pageSize,
            Model model, @ModelAttribute("searchDirectors") DirectorsDto directorsDto
    ) {
        if (directorsDto.getDirectorsFIO().trim().equals("")) {
            model.addAttribute("directors", directorsWithFilmsMapper.toDtos(service.listAll()));
        } else {
            PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "directorsFIO"));
            Page<Directors> directorsPage = service.searchByDirectorsFIO(pageRequest, directorsDto.getDirectorsFIO());
            List<DirectorsWithFilmsDto> directorsDtos = directorsPage
                    .stream()
                    .map(directorsWithFilmsMapper::toDto)
                    .toList();
            model.addAttribute("directors", new PageImpl<>(directorsDtos , pageRequest, directorsPage.getTotalElements()));
        }
        return "directors/viewAllDirectors";
    }

    @GetMapping("/add-films/{directorsId}")
    public String addFilms(Model model, @PathVariable Long directorsId){
        model.addAttribute("films", filmsMapper.toDtos(filmsService.listAll()));
        model.addAttribute("directorsId", directorsId);
        model.addAttribute("directors", mapper.toDto(service.getOne(directorsId)).getDirectorsFIO());
        return "directors/addDirectorsFilms";

    }

    @PostMapping("/add-films")
    public String addFilms(@ModelAttribute("directorsFilmsForm") AddFilmDto addFilmDto){
        service.addFilm(addFilmDto);
        return "redirect:/directors";
    }

}
