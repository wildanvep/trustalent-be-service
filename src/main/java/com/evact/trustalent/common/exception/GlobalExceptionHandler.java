package com.evact.trustalent.common.exception;

import com.evact.trustalent.common.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public <T> ResponseEntity<ResponseDTO<T>> resolveException(Exception exception) {

        String message = exception.getMessage();

        ResponseDTO<T> response = new ResponseDTO<>();

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage(message);
        response.setData(null);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public <T> ResponseEntity<ResponseDTO<T>> resolveException(BadRequestException exception) {

        String message = exception.getMessage();

        ResponseDTO<T> response = new ResponseDTO<>();

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage(message);
        response.setData(null);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public <T> ResponseEntity<ResponseDTO<T>> resolveException(AuthenticationException exception) {

        String message = exception.getMessage();

        ResponseDTO<T> response = new ResponseDTO<>();

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setMessage(message);
        response.setData(null);

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public <T> ResponseEntity<ResponseDTO<T>> resolveException(UsernameNotFoundException exception) {

        String message = exception.getMessage();

        ResponseDTO<T> response = new ResponseDTO<>();

        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(message);
        response.setData(null);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
