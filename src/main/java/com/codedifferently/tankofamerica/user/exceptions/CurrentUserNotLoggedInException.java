package com.codedifferently.tankofamerica.user.exceptions;

public class CurrentUserNotLoggedInException extends Exception{
    public CurrentUserNotLoggedInException(String message) {
        super(message);
    }
}
