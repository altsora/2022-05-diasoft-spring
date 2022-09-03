package ru.diasoft.spring.frontservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.diasoft.spring.frontservice.model.LoginRequest;
import ru.diasoft.spring.frontservice.service.AuthService;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest request, Model model) {
        final Optional<Integer> userId = authService.authorizeUser(request);
        System.err.println("userId = " + userId);
        System.err.println("request = " + request);
        if (userId.isEmpty()) {
            model.addAttribute("userId", null);
            model.addAttribute("request", new LoginRequest());
            return "login";
        }
        return "redirect:/main";
    }
}
