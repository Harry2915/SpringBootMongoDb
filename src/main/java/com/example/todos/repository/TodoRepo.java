package com.example.todos.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.todos.model.Todo;

@Repository
public interface TodoRepo extends MongoRepository<Todo, String> {

	Optional<Todo> findByTodo(String todo);
}
 