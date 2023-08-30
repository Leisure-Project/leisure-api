package com.leisure.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String entity, Long id) {
        super(String.format("%s con id %d no existe.", entity, id));
    }
    public ResourceNotFoundException(String entity, String dni) {
        super(String.format("%s con dni %s no existe.", entity, dni));
    }
    public ResourceNotFoundException(String message){
        super(message);
    }
}
