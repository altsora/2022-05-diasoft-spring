package ru.diasoft.spring.booklibrarymvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.diasoft.spring.booklibrarymvc.exception.DomainNotFoundException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(DomainNotFoundException.class)
    public ResponseEntity<String> handleDomainNotFound(DomainNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
