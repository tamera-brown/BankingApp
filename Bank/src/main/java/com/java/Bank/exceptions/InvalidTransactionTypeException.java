package com.java.Bank.exceptions;

public class InvalidTransactionTypeException extends Exception {
    public InvalidTransactionTypeException(String message){super(message);}
    public InvalidTransactionTypeException(String message, Throwable innerException){super(message,innerException);}
}
