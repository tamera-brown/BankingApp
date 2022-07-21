package com.java.Bank.exceptions;

public class UniqueUserEmailException extends Exception {
    public UniqueUserEmailException(String message){super(message);}
    public UniqueUserEmailException(String message, Throwable innerException){super(message,innerException);}

}
