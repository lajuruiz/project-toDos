package com.example.todos.domain.services;

import com.example.todos.persistence.entities.Todos;
import com.example.todos.domain.repositories.TodosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodosService {

    @Autowired
    TodosRepository todosRepository;

    // CREATE
    public Todos createTodos(Todos todos) {
        return todosRepository.save(todos);
    }

    // READ
    public List<Todos> getTodos() {
        return todosRepository.findAll();
    }
    public Optional<Todos> getById(Integer id) {
        return todosRepository.findById(id);
    }

    // UPDATE
    public Optional<Todos> updateTodos(Integer testId, Todos todosDetails) {
        Optional<Todos> opTodos = todosRepository.findById(testId);

        if(opTodos.isEmpty()) {
            return opTodos;
        }

        Todos todos = opTodos.get();

        todos.setName(todosDetails.getName());
        todos.setDescription(todosDetails.getDescription());
        todos.setDue_date(todosDetails.getDue_date());

        return Optional.of(todosRepository.save(todos));
    }

    // DELETE
    public boolean deleteTodos(Integer id) {
        if (todosRepository.existsById(id)) {
            todosRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}