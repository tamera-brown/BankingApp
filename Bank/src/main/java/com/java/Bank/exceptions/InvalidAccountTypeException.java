package com.java.Bank.exceptions;

public class InvalidAccountTypeException extends Exception{
    public InvalidAccountTypeException(String message){super(message);}
    public InvalidAccountTypeException(String message, Throwable innerException){super(message,innerException);}
}
