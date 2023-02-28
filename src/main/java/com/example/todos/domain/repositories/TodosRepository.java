package com.example.todos.domain.repositories;

import com.example.todos.persistence.entities.Todos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TodosRepository extends JpaRepository<Todos, Integer> {

}