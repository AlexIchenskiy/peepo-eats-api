package com.peepoeats.api.exception;

import com.peepoeats.api.JWT.exception.ExpiredJwtException;
import com.peepoeats.api.JWT.exception.InvalidJwtException;
import com.peepoeats.api.user.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionController {

    // handle all exceptions
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionDetailedResponse> handleAllExceptions(Exception e) {
        List<String> details = new ArrayList<>();

        details.add(e.getLocalizedMessage());

        ExceptionDetailedResponse error = new ExceptionDetailedResponse("Internal server error", details);

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // handle validation exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ExceptionDetailedResponse> methodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        List<String> details = new ArrayList<>();

        // add all invalid fields to response
        for (FieldError fe : e.getFieldErrors())
            details.add(fe.getField() + " -> " + fe.getDefaultMessage());

        ExceptionDetailedResponse error = new ExceptionDetailedResponse("Provided data is invalid", details);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public final ResponseEntity<ExceptionDetailedResponse> handleUserAlreadyExistsException(
            UserAlreadyExistsException e) {
        List<String> details = new ArrayList<>();

        details.add(e.getLocalizedMessage());

        ExceptionDetailedResponse error = new ExceptionDetailedResponse("User already exists", details);

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public final ResponseEntity<ExceptionDetailedResponse> handleExpiredJwtException(ExpiredJwtException e) {
        List<String> details = new ArrayList<>();

        details.add(e.getLocalizedMessage());

        ExceptionDetailedResponse error = new ExceptionDetailedResponse("Expired JWT token", details);

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidJwtException.class)
    public final ResponseEntity<ExceptionDetailedResponse> handleInvalidJwtException(InvalidJwtException e) {
        List<String> details = new ArrayList<>();

        details.add(e.getLocalizedMessage());

        ExceptionDetailedResponse error = new ExceptionDetailedResponse("Invalid JWT token", details);

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

}
