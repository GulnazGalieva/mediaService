package com.example.mediaservice.cotroller;

import com.example.mediaservice.dto.GenericDto;
import com.example.mediaservice.mapper.GenericMapper;
import com.example.mediaservice.model.GenericModel;
import com.example.mediaservice.service.GenericService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public abstract class GenericController<T extends GenericModel, N extends GenericDto> {
    private final GenericService<T> service;
    private final GenericMapper<T, N> mapper;

    protected GenericController(GenericService<T> service, GenericMapper<T, N> mapper) {
        this.service = service;
        this.mapper = mapper;
    }


    @GetMapping("/list")
    @Operation(description = "Получить все записи", method = "GetAll")
    public ResponseEntity<List<N>> getAll(){
        return ResponseEntity.ok().body(mapper.toDtos(service.listAll()));
    }

    @Operation(description = "Получить запись по id", method = "GetOne")
    @GetMapping("/{id}")//localhost:9090/rest/user/1
    public ResponseEntity<N> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDto(service.getOne(id)));
    }
    //orElseThrow - опракидывает в exception

    @Operation(description = "Создать запись", method = "Create")
    @PostMapping("/creat")
    public ResponseEntity<N> create(@RequestBody N object){
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDto(service.create(mapper.toEntity(object))));
    }

    @Operation(description = "Обновить запись", method = "Update")
    @PutMapping("/{id}")
    public ResponseEntity<N>  update(@RequestBody N object, @PathVariable Long id){
        object.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDto(service.update(mapper.toEntity(object))));
    }

    @Operation(description = "Удалить запись", method = "Delete")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
       service.delete(id);
    }
}
