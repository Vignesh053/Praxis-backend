package com.origami.Praxisbackend.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String fieldValue;
    private String fieldName;

    private String resourceName;
    public ResourceNotFoundException(String resourceName, String fieldName,  String fieldValue){
        super(String.format("%s not found with %s: %s", resourceName, fieldName, fieldValue));

        this.fieldName = fieldName;
        this.resourceName = resourceName;
        this.fieldValue = fieldValue;
    }
}
