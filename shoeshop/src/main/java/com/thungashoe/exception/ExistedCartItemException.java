package com.thungashoe.exception;

import javax.management.RuntimeErrorException;

public class ExistedCartItemException extends RuntimeErrorException{

	private static final long serialVersionUID = 1L;

	public ExistedCartItemException(Error e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

	
}
