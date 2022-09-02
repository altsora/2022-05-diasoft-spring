package ru.diasoft.spring.commonsspringbootauthoconfigure.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

import static ru.diasoft.spring.commonsspringbootauthoconfigure.utils.CommonConstants.*;
import static ru.diasoft.spring.commonsspringbootauthoconfigure.utils.CommonConstants.DOMAIN_NOT_FOUND_EXCEPTION;

@Log4j2
@ControllerAdvice
@ConditionalOnProperty("commons.rest-exception-handler.enabled")
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @PostConstruct
    private void init() {
        log.info("Initializing ResponseEntityExceptionHandler 'restExceptionHandler'");
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        final List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        final ErrorResponse response = ErrorResponse.builder()
                .exception(METHOD_ARGUMENT_NOT_VALID_EXCEPTION)
                .message(METHOD_ARGUMENT_NOT_VALID_EXCEPTION_MESSAGE)
                .errors(errors)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        final ErrorResponse response = ErrorResponse.builder()
                .exception(HTTP_MESSAGE_NOT_READABLE_EXCEPTION)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DomainNotFoundException.class)
    public ResponseEntity<Object> handleEntity(DomainNotFoundException ex) {
        final ErrorResponse response = ErrorResponse.builder()
                .exception(DOMAIN_NOT_FOUND_EXCEPTION)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Data
    @Builder
    @AllArgsConstructor
    private static class ErrorResponse {
        private final String exception;
        private final String message;

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private List<String> errors;
    }
}
