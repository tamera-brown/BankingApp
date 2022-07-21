package com.java.Bank.exceptions;

public class InvalidUserIdException extends Exception {
    public InvalidUserIdException(String message){super(message);}
    public InvalidUserIdException(String message,Throwable innerException){super(message,innerException);}
}
