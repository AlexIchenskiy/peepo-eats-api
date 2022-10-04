package com.peepoeats.api.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserBadCredentialsException extends RuntimeException {

    public UserBadCredentialsException(String message) {
        super("Could not authorize user " + message);
    }

}
