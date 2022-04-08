package com.example.todos.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import com.example.todos.exception.TodoCustomException;
import com.example.todos.model.Todo;

public interface TodoService {

	void credateTodo(Todo todo) throws ConstraintViolationException, TodoCustomException;

	void deleteTodo(String Id) throws TodoCustomException;

	List<Todo> getAllTodos();

	Todo getTodoById(String Id) throws TodoCustomException;

	void updateTodo(String Id, Todo todo) throws TodoCustomException;
}
