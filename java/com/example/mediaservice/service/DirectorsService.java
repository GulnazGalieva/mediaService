package com.example.mediaservice.service;

import com.example.mediaservice.dto.AddFilmDto;
import com.example.mediaservice.model.Directors;
import com.example.mediaservice.model.Films;
import com.example.mediaservice.repository.DirectorsRepository;
import com.example.mediaservice.repository.FilmsRepository;
import com.example.mediaservice.repository.OrdersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springdoc.api.OpenApiResourceNotFoundException;

import java.util.List;

@Service
public class DirectorsService extends GenericService<Directors>{

    private final DirectorsRepository directorsRepository;
    private final FilmsService filmsService;
    private final FilmsRepository filmsRepository;
    private final OrdersRepository ordersRepository;

    protected DirectorsService(DirectorsRepository directorsRepository,
                               FilmsService filmsService, FilmsRepository filmsRepository, OrdersRepository ordersRepository) {
        super(directorsRepository);
        this.directorsRepository = directorsRepository;
        this.filmsService = filmsService;
        this.filmsRepository = filmsRepository;
        this.ordersRepository = ordersRepository;
    }

    public Page<Directors> searchByDirectorsFIO(Pageable pageable,String fio){
        return directorsRepository.findAllByDirectorsFIO(pageable,fio);
    }

    public Page<Directors> listAllPaginated(Pageable pageable){
        return directorsRepository.findAll(pageable);
    }

    public Directors addFilm(AddFilmDto addFilmDto){
        Directors directors = getOne(addFilmDto.getDirectorsId());
        Films films = filmsService.getOne(addFilmDto.getFilmsId());
        directors.getFilms().add(films);
        return update(directors);
    }
    @Override
    public void delete(Long id) {
        List<Films> films = filmsRepository.findFilmsByDirectorsId(id);
        if(films.isEmpty()) {
            directorsRepository.deleteById(id);
            return;
        }
        List<Long> filmsIds = films.stream()
                .filter(i -> i.getDirectors().size() == 1)
                .map(Films::getId)
                .toList();

        if (filmsIds.isEmpty())
            throw new OpenApiResourceNotFoundException("У данного актера нету фильма где он снимался один.");
        filmsIds.stream()
                .filter(i -> {
                    if (ordersRepository.findOrdersByFilmsId(i).isEmpty()) {
                        return true;
                    } else {
                        throw new OpenApiResourceNotFoundException("Фильм с данным актерем в данный момент арендован");
                    }
                })
                .forEach(i -> {
                    filmsRepository.deleteById(i);
                    directorsRepository.deleteById(id);
                });
    }




//Реализовать проверки на удаление участника фильма (таблица Directors). Удаляем
//участника, если нет фильмов в аренде, также удаляем все фильмы где этот участник был
//единственным.

//    @Override
//    public void delete(Long id) {
//        if(directorsRepository.getReferenceById(id).getFilms().isEmpty()){
//            directorsRepository.deleteById(id);
//        }
//        Set<Films> filmsSet = directorsRepository.getReferenceById(id).getFilms();
//        filmsSet.forEach(i->{
//            Set<Directors> directors=i.getDirectors();
//            int countDirectors = directors.size();
//            if(countDirectors < 2 && ordersRepository.findOrdersByFilms(i).isEmpty()){
//                directorsRepository.deleteById(id);
//            }
//        });
//    }
}
