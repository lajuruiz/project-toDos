package com.example.todos.controllers;

import com.example.todos.persistence.entities.Todos;
import com.example.todos.domain.services.TodosService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TodosController {
    @Autowired
    TodosService todosService;

    @GetMapping(value="/todos")
    public ResponseEntity<List<Todos>> getList(HttpServletResponse response) {
        List<Todos> listTodos = todosService.getTodos();
        if(listTodos.size() == 0){
            return new ResponseEntity<>(listTodos, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listTodos, HttpStatus.OK);
    }

    @GetMapping(value="/todos/{todosId}")
    public ResponseEntity<Todos> getById(@PathVariable(value = "todosId") Integer id) {
        return ResponseEntity.of(todosService.getById(id));
    }

    @PostMapping(value="/todos")
    public ResponseEntity<Todos> createTodos(@Valid @RequestBody Todos todos) {
        return new ResponseEntity<>(todosService.createTodos(todos), HttpStatus.CREATED);
    }

    @PutMapping(value="/todos/{todosId}")
    public ResponseEntity<Todos> updateTodos(@Valid @PathVariable(value = "todosId") Integer id, @RequestBody Todos todoDetails) {
        return todosService.updateTodos(id, todoDetails)
                .map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value="/todos/{todosId}")
    public void deleteTodos(@PathVariable(value = "todosId") Integer id, HttpServletResponse response) {
        if(todosService.deleteTodos(id)){
            response.setStatus(HttpStatus.OK.value());
        } else {
            response.setStatus(HttpStatus.NO_CONTENT.value());
        }
    }

}