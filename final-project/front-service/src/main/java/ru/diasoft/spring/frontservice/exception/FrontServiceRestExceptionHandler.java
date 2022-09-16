package ru.diasoft.spring.frontservice.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.diasoft.spring.commonsspringbootauthoconfigure.exception.RestExceptionHandler;
import ru.diasoft.spring.commonsspringbootauthoconfigure.spring.InitLog;

@Log4j2
@InitLog
@ControllerAdvice
public class FrontServiceRestExceptionHandler extends RestExceptionHandler {
    @ExceptionHandler(NoAuthException.class)
    public String handleNoAuth(final NoAuthException ex, final Model model) {
        return "redirect:/auth/login";
    }
}
