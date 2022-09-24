package com.peepoeats.api.JWT.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InvalidJwtException extends RuntimeException {

    public InvalidJwtException(String message) {
        super("Provided JWT token is invalid: " + message);
    }

}
