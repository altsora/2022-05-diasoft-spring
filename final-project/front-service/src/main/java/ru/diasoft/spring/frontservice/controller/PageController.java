package ru.diasoft.spring.frontservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.diasoft.spring.commonsspringbootauthoconfigure.aop.Loggable;
import ru.diasoft.spring.frontservice.service.AuthService;

import static ru.diasoft.spring.frontservice.utils.FrontServiceConstants.PAGE_INDEX;

@Log4j2
@Loggable
@Controller
@RequiredArgsConstructor
public class PageController {
    private final AuthService authService;
    @GetMapping("/")
    public String index() {
        if (authService.isUserAuthorize()) {
            return "redirect:/main";
        } else  {
            return PAGE_INDEX;
        }
    }
}
