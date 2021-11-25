package com.company.microtraining.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandler {
	//TODO change response length
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> methodArgumentHandler(MethodArgumentTypeMismatchException ex){
        return new ResponseEntity<>("NOT A VALID PARAM INPUT. MUST BE A NUMBER", HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> noSuchElementHandler(NoSuchElementException ex){
        return new ResponseEntity<>("CUSTOMER NOT FOUND", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> nullPointerHandler(NullPointerException ex){
        return new ResponseEntity<>("MISSING PARAMS IN INPUT. CHECK PARAM LIST", HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> messageNotReadbleHandler(HttpMessageNotReadableException ex){
        return new ResponseEntity<>("NOT A VALID INPUT. MUST BE A INTEGER", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
