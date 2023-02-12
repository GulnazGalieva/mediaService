package com.example.mediaservice.repository;

import com.example.mediaservice.model.Directors;
import com.example.mediaservice.model.Films;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DirectorsRepository extends GenericRepository<Directors>{
    Set<Directors> findAllByIdIn(Set<Long> ids);
    Page<Directors> findAllByDirectorsFIO(Pageable pageable,String directorsFIO);


}
