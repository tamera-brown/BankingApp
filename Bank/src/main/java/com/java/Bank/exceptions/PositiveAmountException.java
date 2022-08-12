package com.java.Bank.exceptions;

public class PositiveAmountException extends Exception{
    public PositiveAmountException(String message){super(message);}
    public PositiveAmountException(String message,Throwable innerException){super(message,innerException);}
}
