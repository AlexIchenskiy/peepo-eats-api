package com.peepoeats.api.JWT.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class ExpiredJwtException extends RuntimeException {

    public ExpiredJwtException(String message) {
        super("Provided JWT token has expired: " + message);
    }

}
