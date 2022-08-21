package com.java.Bank.exceptions;

public class InvalidUsernameException extends Exception {
    public InvalidUsernameException(String message) {
        super(message);
    }
    public InvalidUsernameException(String message, Throwable innerException){
        super(message,innerException);
    }
}
