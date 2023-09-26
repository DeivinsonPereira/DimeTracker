package com.deivinson.gerenciadordespesas.services.exceptions;

public class InvalidInputException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public InvalidInputException (String msg) {
		super(msg);
	}
}
