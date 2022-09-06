package ru.diasoft.spring.frontservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.request.LoginRequest;

@Controller
@RequiredArgsConstructor
public class PageController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("request", new LoginRequest());
        model.addAttribute("userId", -1);
        return "login";
    }
}
