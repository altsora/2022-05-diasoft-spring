package ru.diasoft.spring.frontservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.diasoft.spring.commonsspringbootauthoconfigure.aop.Loggable;

@Log4j2
@Loggable
@Controller
@RequiredArgsConstructor
public class PageController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
