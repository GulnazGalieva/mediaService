package com.example.mediaservice.service;

import com.example.mediaservice.model.GenericModel;
import com.example.mediaservice.repository.GenericRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public abstract class GenericService<T extends GenericModel>{
    private final GenericRepository<T> repository;

    protected GenericService(GenericRepository<T> repository) {
        this.repository = repository;
    }
    public List<T> listAll(){
        return repository.findAll();
    }
    public T getOne(Long id){
        return repository.findById(id).orElseThrow(()-> new NotFoundException("Row with such ID: " + id + " not found"));
    }
    public T create (T object){
        return repository.save(object);
    }
    public  T update (T object){
        return repository.save(object);
    }
    public  void delete (Long id){
        repository.deleteById(id);
    }
}
