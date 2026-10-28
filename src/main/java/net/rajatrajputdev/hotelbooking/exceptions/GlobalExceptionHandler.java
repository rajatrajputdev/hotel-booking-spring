package net.rajatrajputdev.hotelbooking.exceptions;

import com.mongodb.MongoWriteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity<String> handleDuplicateError(MongoWriteException exception) {
        return new ResponseEntity<>("Duplicate entry detected. Kindly change the data.", HttpStatus.NOT_ACCEPTABLE);
    }
}