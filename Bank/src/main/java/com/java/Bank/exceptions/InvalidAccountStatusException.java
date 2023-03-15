package com.java.Bank.exceptions;

public class InvalidAccountStatusException extends Exception {
    public InvalidAccountStatusException(String message){super(message);}
    public InvalidAccountStatusException(String message, Throwable innerException){super(message,innerException);}
}
