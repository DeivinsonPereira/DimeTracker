package com.deivinson.gerenciadordespesas.services.exceptions;

public class DataInvalidaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	
	public DataInvalidaException (String msg) {
		super(msg);
	}
}