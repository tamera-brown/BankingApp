package com.java.Bank.exceptions;

public class InvalidTransactionIdException extends Exception {
    public InvalidTransactionIdException(String message){super(message);}
    public InvalidTransactionIdException(String message, Throwable innerException){super(message,innerException);}
}
