package com.example.todos.exception;

public class TodoCustomException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String NotFoundException(String id) {
		return "Todo with id " + id + " not found";
	}

	public static String TodoAlreadyExists() {
		return "Todo with given name already exists";
	}

	public TodoCustomException(String message) {
		super(message);
	}
}
