package com.origami.Praxisbackend.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_GATEWAY)
public class EmailAlreadyExistsException extends RuntimeException {
    private String email;
    public EmailAlreadyExistsException(String email){
        super(String.format("The email %s already exists", email));
        this.email = email;
    }
}
