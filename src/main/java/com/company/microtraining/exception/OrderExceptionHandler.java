package com.company.microtraining.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class OrderExceptionHandler {
	//TODO change response length
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> methodArgumentHandler(MethodArgumentTypeMismatchException ex){
        return new ResponseEntity<>("NOT A VALID PARAM INPUT. MUST BE A NUMBER", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> noSuchElementHandler(NoSuchElementException ex){
        return new ResponseEntity<>("THE ELEMENT WITH THE DESIRED ID WAS NOT FOUND", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> nullPointerHandler(NullPointerException ex){
        return new ResponseEntity<>("MISSING PARAMS IN INPUT. CHECK PARAM LIST", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> messageNotReadbleHandler(HttpMessageNotReadableException ex){
        return new ResponseEntity<>("NOT A VALID INPUT. MUST BE A INTEGER", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> noValuesInDBHandler(IllegalStateException ex){
        return new ResponseEntity<>("NO DATA IN DB", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
