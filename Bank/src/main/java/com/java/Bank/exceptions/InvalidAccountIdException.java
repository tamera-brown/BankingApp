package com.java.Bank.exceptions;

public class InvalidAccountIdException extends Exception {
    public InvalidAccountIdException(String message){super(message);}
    public InvalidAccountIdException(String message, Throwable innerException){super(message,innerException);}
}
