package com.example.mediaservice.service;

import com.example.mediaservice.model.Directors;
import com.example.mediaservice.model.Films;
import com.example.mediaservice.repository.DirectorsRepository;
import com.example.mediaservice.repository.GenericRepository;
import com.example.mediaservice.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DirectorsService extends GenericService<Directors>{

    private final DirectorsRepository directorsRepository;
    private final OrdersRepository ordersRepository;
    protected DirectorsService(GenericRepository<Directors> repository, DirectorsRepository directorsRepository,
                               OrdersRepository ordersRepository) {
        super(repository);

        this.directorsRepository = directorsRepository;

        this.ordersRepository = ordersRepository;
    }
//Реализовать проверки на удаление участника фильма (таблица Directors). Удаляем
//участника, если нет фильмов в аренде, также удаляем все фильмы где этот участник был
//единственным.
    //Никита, метод удаляет. Только есть один нюанс:
    // если у участника нет фильма(у меня есть такие), метод удаляет его из базы, а потом
    //выводит 500 ошибку. Интересно, почему?
    @Override
    public void delete(Long id) {
        if(directorsRepository.getReferenceById(id).getFilms().isEmpty()){
            directorsRepository.deleteById(id);
        }
        Set<Films> filmsSet = directorsRepository.getReferenceById(id).getFilms();
        filmsSet.forEach(i->{
            Set<Directors> directors=i.getDirectors();
            int countDirectors = directors.size();
            if(countDirectors < 2 && ordersRepository.findOrdersByFilms(i).isEmpty()){
                directorsRepository.deleteById(id);
            }
        });
    }
}
