package com.example.mediaservice.MVC.controller;

import com.example.mediaservice.dto.AddFilmDto;
import com.example.mediaservice.dto.FilmsDto;
import com.example.mediaservice.dto.FilmsWithDirectorsDto;
import com.example.mediaservice.mapper.DirectorsMapper;
import com.example.mediaservice.mapper.FilmsMapper;
import com.example.mediaservice.mapper.FilmsWithDirectorsMapper;
import com.example.mediaservice.model.Films;
import com.example.mediaservice.repository.DirectorsRepository;
import com.example.mediaservice.service.FilmsService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
@Controller
@RequestMapping("/films")
public class MVCFilmsController {
    private final FilmsService service;
    private final FilmsWithDirectorsMapper filmsWithDirectorsMapper;
    private final FilmsMapper mapper;
    private final DirectorsMapper directorsMapper;
    private final DirectorsRepository directorsRepository;


    public MVCFilmsController(FilmsService service, FilmsWithDirectorsMapper filmsWithDirectorsMapper, FilmsMapper mapper, DirectorsMapper directorsMapper,
                              DirectorsRepository directorsRepository) {
        this.service = service;
        this.filmsWithDirectorsMapper = filmsWithDirectorsMapper;
        this.mapper = mapper;
        this.directorsMapper = directorsMapper;
        this.directorsRepository = directorsRepository;
    }

    @GetMapping("")
    public String getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "5") int pageSize,
                         Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "title"));
        Page<Films> filmsPage = service.listAllPaginated(pageRequest);
        List<FilmsWithDirectorsDto> filmsDtos = filmsPage
                .stream()
                .map(filmsWithDirectorsMapper::toDto)
                .toList();
        model.addAttribute("films", new PageImpl<>(filmsDtos, pageRequest, filmsPage.getTotalElements()));
        return "films/viewAllFilms";
    }

    @GetMapping("/add")
    public String create() {
        return "films/addFilms";// путь до html файла
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("filmsForm") FilmsDto filmsDto) {
        service.create(mapper.toEntity(filmsDto));
        return "redirect:/films";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/films";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("films", filmsWithDirectorsMapper.toDto(service.getOne(id)));
        return "films/updateFilms";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("filmsForm") FilmsDto filmsDto) {
        service.update(mapper.toEntity(filmsDto));
        return "redirect:/films";
    }

    @PostMapping("/search")
    public String searchByTitleFilms(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int pageSize,
            Model model,
            @ModelAttribute("searchFilms") FilmsDto filmsDto) {
        if (filmsDto.getTitle().trim().equals("")) {
            model.addAttribute("films",filmsWithDirectorsMapper.toDtos(service.listAll()));
        } else {
            PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "title"));
            Page<Films> filmsPage = service.searchByTitle(pageRequest, filmsDto.getTitle());
            List<FilmsWithDirectorsDto> filmsDtos = filmsPage
                    .stream()
                    .map(filmsWithDirectorsMapper::toDto)
                    .toList();
            model.addAttribute("films", new PageImpl<>(filmsDtos, pageRequest, filmsPage.getTotalElements()));
        }
        return "films/viewAllFilms";
    }

    @GetMapping("/add-directors/{filmsId}")
    public String addDirectors(Model model, @PathVariable Long filmsId){
        model.addAttribute("directors", directorsMapper.toDtos(directorsRepository.findAll()));
        model.addAttribute("filmsId", filmsId);
        model.addAttribute("films", mapper.toDto(service.getOne(filmsId)).getTitle());
        return "films/addFilmsDirectors";

    }

    @PostMapping("/add-directors")
    public String addDirectors(@ModelAttribute("filmsDirectorsForm") AddFilmDto addFilmDto){
        service.addDirectors(addFilmDto);
        return "redirect:/films";
    }
}


