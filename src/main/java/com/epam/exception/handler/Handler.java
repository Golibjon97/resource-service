package com.epam.exception.handler;

import com.epam.exception.KafkaException;
import com.epam.exception.MetadataNotFoundException;
import com.epam.exception.ResourceNotFoundException;
import com.epam.exception.response.ExceptionInformation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Handler {

    @ExceptionHandler({ResourceNotFoundException.class, MetadataNotFoundException.class})
    public ResponseEntity<ExceptionInformation> handleNotFoundException(Exception e){
        ExceptionInformation exceptionInformation = ExceptionInformation.builder()
                .status(HttpStatus.NOT_FOUND)
                .code("404")
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionInformation);
    }

    @ExceptionHandler(KafkaException.class)
    public ResponseEntity<ExceptionInformation> handleKafkaException(KafkaException e){
        ExceptionInformation exceptionInformation = ExceptionInformation.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionInformation);
    }
}
