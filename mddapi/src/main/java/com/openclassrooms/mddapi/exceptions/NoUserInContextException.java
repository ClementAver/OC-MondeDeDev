package com.openclassrooms.mddapi.exceptions;

public class NoUserInContextException extends Exception{
    public NoUserInContextException(String message) {
        super(message);
    }
}
