package com.example.todos.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todos.exception.TodoCustomException;
import com.example.todos.model.Todo;
import com.example.todos.service.TodoService;

@RestController
@RequestMapping("api/todo/")
public class TodoController {

	@Autowired
	private TodoService service;

	@PostMapping("createTodo")
	public ResponseEntity<?> credateTodo(@RequestBody Todo todo) {
		try {
			service.credateTodo(todo);
			return new ResponseEntity<>(todo, HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (TodoCustomException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping("/deleteTodo/{id}")
	public ResponseEntity<?> deleteTodo(@PathVariable("id") String Id) {
		try {
			service.deleteTodo(Id);
			return new ResponseEntity<>("Todo deleted successfully with id " + Id, HttpStatus.OK);
		} catch (TodoCustomException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("getall")
	public ResponseEntity<?> getAllTodos() {
		List<Todo> todoList = service.getAllTodos();
		if (todoList.size() > 0)
			return new ResponseEntity<>(todoList, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("getById/{id}")
	public ResponseEntity<?> getTodoById(@PathVariable("id") String Id) {
		try {
			Todo todo = service.getTodoById(Id);
			return new ResponseEntity<>(todo, HttpStatus.OK);
		} catch (TodoCustomException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("updateTodo/{id}")
	public ResponseEntity<?> updateTodo(@PathVariable("id") String Id, @RequestBody Todo todo) {
		try {
			 service.updateTodo(Id, todo);
			return new ResponseEntity<>("Updated todo with id " +Id, HttpStatus.OK);
		} catch (TodoCustomException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (ConstraintViolationException e) {    ////////checks for null values add dependency starter validation to use this
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}
}
