package com.project.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class LackOfResourceException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public LackOfResourceException(String message) {
        super(message);
    }
}