package com.johnbryce.couponsystemphase2.Exception;

public class NotExistException extends Exception {
	
	public NotExistException(String message) {
		super(message + " Not exist Exception ");
	}
}
