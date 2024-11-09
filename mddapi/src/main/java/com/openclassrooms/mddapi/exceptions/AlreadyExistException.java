package com.openclassrooms.mddapi.exceptions;

public class AlreadyExistException extends Exception{
    public AlreadyExistException (String message) {
        super(message);
    }
}
