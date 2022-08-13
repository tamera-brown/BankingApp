package com.java.Bank.exceptions;

public class DuplicateAccountException extends Exception{
    public DuplicateAccountException(String message){super(message);}
    public DuplicateAccountException(String message, Throwable innerException){super(message,innerException);}
}
