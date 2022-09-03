package ru.diasoft.spring.commonsspringbootauthoconfigure.exception;

import lombok.extern.log4j.Log4j2;
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
import ru.diasoft.spring.commonsspringbootauthoconfigure.utils.BaseResponse;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

import static ru.diasoft.spring.commonsspringbootauthoconfigure.utils.CommonConstants.*;

@Log4j2
@ControllerAdvice
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
        final BaseResponse response = new BaseResponse()
                .setException(METHOD_ARGUMENT_NOT_VALID_EXCEPTION)
                .setRetMessage(METHOD_ARGUMENT_NOT_VALID_EXCEPTION_MESSAGE)
                .setErrors(errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        final BaseResponse response = new BaseResponse()
                .setException(HTTP_MESSAGE_NOT_READABLE_EXCEPTION)
                .setRetMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DomainNotFoundException.class)
    public ResponseEntity<Object> handleEntity(DomainNotFoundException ex) {
        final BaseResponse response = new BaseResponse()
                .setException(DOMAIN_NOT_FOUND_EXCEPTION)
                .setRetMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
