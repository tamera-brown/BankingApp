package com.java.Bank.exceptions;

public class InvalidUserEmailException extends Exception {
    public InvalidUserEmailException(String message){super(message);}
    public InvalidUserEmailException(String message,Throwable innerException){super(message,innerException);}
}
