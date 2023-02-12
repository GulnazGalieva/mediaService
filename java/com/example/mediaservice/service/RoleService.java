package com.example.mediaservice.service;

import com.example.mediaservice.model.Role;
import com.example.mediaservice.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository  repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public List<Role> listAll(){
        return repository.findAll();
    }

    public Role getOne(Long id){
        return repository.findById(id).orElseThrow();
    }
}
