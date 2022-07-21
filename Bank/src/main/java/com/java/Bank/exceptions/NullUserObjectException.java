package com.java.Bank.exceptions;

public class NullUserObjectException extends Exception {
    public NullUserObjectException(String message){super(message);}
    public NullUserObjectException(String message,Throwable innerException){super(message,innerException);}
}
