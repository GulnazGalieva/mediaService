package com.example.mediaservice.service;

import com.example.mediaservice.dto.AddFilmDto;
import com.example.mediaservice.model.Directors;
import com.example.mediaservice.model.Films;
import com.example.mediaservice.model.Genre;
import com.example.mediaservice.repository.DirectorsRepository;
import com.example.mediaservice.repository.FilmsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FilmsService extends GenericService<Films>  {
    private final FilmsRepository filmsrepository;
    private final DirectorsRepository directorsRepository;



    protected FilmsService(FilmsRepository filmsrepository, DirectorsRepository directorsRepository) {
        super(filmsrepository);
        this.filmsrepository = filmsrepository;
        this.directorsRepository = directorsRepository;
    }


    public Page<Films> listAllPaginated(Pageable pageable){
        return filmsrepository.findAll(pageable);
    }
    public Page<Films> searchByTitle(Pageable pageable,String title){
        return filmsrepository.findAllByTitle(pageable,title);
    }

    public List<Films> search(String title, Genre genre, String country){
        return filmsrepository.findAllByTitleOrGenreOrCountry(
              title,genre,country
        );
    }

    public Films addDirectors(AddFilmDto addFilmDto){
        Films films = getOne(addFilmDto.getFilmsId());
        Directors directors = directorsRepository.findById(addFilmDto.getDirectorsId()).orElseThrow();
        films.getDirectors().add(directors);
        return update(films);
    }





    //Добавить участников к фильму. Данный метод я реализовала здесь, так как у сущности Directors стоит
//    //@JsonIgnore  и mappedBy(если я правильно поняла, mappedBy - говорит, что данная таблица второстепенная
//    // (то есть не главная)).
//    public Films addDirectors(Long id, Long ids) {
//        Set<Directors> directors = new HashSet<>(filmsrepository.findById(ids).orElseThrow().getDirectors());
//        directors.add(directorsRepository.findById(id).orElseThrow());
//        filmsrepository.findById(ids).orElseThrow().setDirectors(directors);
//        return filmsrepository.findById(ids).orElseThrow();
//    }

}
