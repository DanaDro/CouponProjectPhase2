package com.johnbryce.couponsystemphase2.Exception;

public class OperationNotAllowedExeption extends Exception {
	
	public OperationNotAllowedExeption(String message) {
		super(message + " is not allowed Exception.");
	}
}
