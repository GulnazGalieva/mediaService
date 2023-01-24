package com.example.mediaservice.service;

import com.example.mediaservice.model.Directors;
import com.example.mediaservice.model.Films;
import com.example.mediaservice.model.Genre;
import com.example.mediaservice.repository.DirectorsRepository;
import com.example.mediaservice.repository.FilmsRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FilmsService extends GenericService<Films>{
    private final FilmsRepository filmsrepository;
    private final DirectorsRepository directorsRepository;

    protected FilmsService(FilmsRepository filmsrepository, DirectorsRepository directorsRepository) {
        super(filmsrepository);
        this.filmsrepository = filmsrepository;

        this.directorsRepository = directorsRepository;
    }
    //Добавить участников к фильму. Данный метод я реализовала здесь, так как у сущности Directors стоит
    //@JsonIgnore  и mappedBy(если я правильно поняла, mappedBy - говорит, что данная таблица второстепенная
    // (то есть не главная)).
    public Films addDirectors(Long id, Long ids) {
        Set<Directors> directors = new HashSet<>(filmsrepository.findById(ids).orElseThrow().getDirectors());
        directors.add(directorsRepository.findById(id).orElseThrow());
        filmsrepository.findById(ids).orElseThrow().setDirectors(directors);
        return filmsrepository.findById(ids).orElseThrow();
    }
    //Реализовать поиск фильма в сервисе по названию/стране/жанру. Поиск должен
    //работать, если даже введен хотя бы один критерий на вход.
    public List<Films> search(String title, Genre genre, String country){
        return filmsrepository.findAllByTitleOrGenreOrCountry(
              title,genre,country
        );
    }


}
