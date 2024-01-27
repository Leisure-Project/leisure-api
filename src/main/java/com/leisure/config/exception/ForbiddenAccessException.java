package com.leisure.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenAccessException extends RuntimeException{
    public ForbiddenAccessException(){
        super("No cuenta con los permisos necesarios para realizar esta acción");
    }
}
