package com.example.mediaservice.repository;

import com.example.mediaservice.model.Directors;
import com.example.mediaservice.model.Films;
import com.example.mediaservice.model.Genre;
import com.example.mediaservice.model.Orders;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface FilmsRepository extends GenericRepository<Films>{
    List<Films> findAllByTitleOrGenreOrCountry(String title, Genre genre, String country);


}
