package com.java.Bank.exceptions;

public class InvalidAccountNumException extends Exception {
    public InvalidAccountNumException(String message) {
        super(message);
    }
    public InvalidAccountNumException(String message,Throwable innerException){
        super(message,innerException);
    }
}
