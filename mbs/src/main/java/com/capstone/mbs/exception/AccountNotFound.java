package com.capstone.mbs.exception;

public class AccountNotFound extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AccountNotFound(String message) {
		super(message);
	}
}