package com.example.todos.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todos.exception.TodoCustomException;
import com.example.todos.model.Todo;
import com.example.todos.repository.TodoRepo;
import com.example.todos.service.TodoService;

@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	private TodoRepo todoRepo;

	@Override
	public void credateTodo(Todo todo) throws ConstraintViolationException, TodoCustomException {
		Optional<Todo> todoOptional = todoRepo.findByTodo(todo.getTodo());
		if (todoOptional.isPresent()) {
			throw new TodoCustomException(TodoCustomException.TodoAlreadyExists());
		}
		todo.setCreatedAt(new Date(System.currentTimeMillis()));
		todoRepo.save(todo);

	}

	@Override
	public void deleteTodo(String Id) throws TodoCustomException {
		Optional<Todo> todoOptional = todoRepo.findById(Id);
		if (!todoOptional.isPresent())
			throw new TodoCustomException(TodoCustomException.NotFoundException(Id));
		todoRepo.deleteById(Id);

	}

	@Override
	public List<Todo> getAllTodos() {
		List<Todo> todos = todoRepo.findAll();
		if (todos.size() > 0)
			return todos;
		return new ArrayList<>();
	}

	@Override
	public Todo getTodoById(String Id) throws TodoCustomException {
		Optional<Todo> toOptional = todoRepo.findById(Id);
		if (!toOptional.isPresent()) {
			throw new TodoCustomException(TodoCustomException.NotFoundException(Id));
		}

		return (toOptional.get());
	}

	@Override
	public void updateTodo(String Id, Todo todo) throws TodoCustomException {
		Optional<Todo> todoOptional = todoRepo.findById(Id);
		Optional<Todo> todoWithSameName = todoRepo.findByTodo(todo.getTodo());
		if (!todoOptional.isPresent())
			throw new TodoCustomException(TodoCustomException.NotFoundException(Id));
		if (todoWithSameName.isPresent() && !todoWithSameName.get().getId().equals(Id))
			throw new TodoCustomException(TodoCustomException.TodoAlreadyExists());
		Todo todo2 = todoOptional.get();
		todo2.setTodo(todo.getTodo());
		todo2.setDescription(todo.getDescription());
		todo2.setUpdatedAt(new Date(System.currentTimeMillis()));
		todo2.setCompleted(todo.getCompleted());
		todoRepo.save(todo2);
	}

}
