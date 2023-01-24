package com.example.mediaservice.cotroller;

import com.example.mediaservice.model.GenericModel;
import com.example.mediaservice.service.GenericService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public abstract class GenericController<T extends GenericModel> {
    private final GenericService<T> service;

    protected GenericController(GenericService<T> service) {
        this.service = service;
    }


    @GetMapping("/list")
    @Operation(description = "Получить все записи", method = "GetAll")
    ResponseEntity<?> getAll(){
        return ResponseEntity.ok().body(service.listAll());
    }

    @Operation(description = "Получить запись по id", method = "GetOne")
    @GetMapping("/{id}")//localhost:9090/rest/user/1
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getOne(id));
    }
    //orElseThrow - опракидывает в exception

    @Operation(description = "Создать запись", method = "Create")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody T object){
        return ResponseEntity.status(HttpStatus.OK).body(service.create(object));
    }

    @Operation(description = "Обновить запись", method = "Update")
    @PutMapping("/{id}")
    public ResponseEntity<?>  update(@RequestBody T object, @PathVariable Long id){
        object.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(service.update(object));
    }

    @Operation(description = "Удалить запись", method = "Delete")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
       service.delete(id);
    }
}
