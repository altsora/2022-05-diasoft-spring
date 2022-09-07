package ru.diasoft.spring.frontservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.diasoft.spring.commonsspringbootauthoconfigure.aop.Loggable;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.request.LoginRequest;
import ru.diasoft.spring.frontservice.service.AuthService;

import java.util.Optional;

import static ru.diasoft.spring.frontservice.utils.FrontServiceConstants.MODEL_IS_USER_AUTH;
import static ru.diasoft.spring.frontservice.utils.FrontServiceConstants.MODEL_LOGIN_REQUEST;

@Log4j2
@Loggable
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public String loginForm(@ModelAttribute LoginRequest request, Model model) {
        final Optional<Integer> userId = authService.authorizeUser(request);
        if (userId.isEmpty()) {
            model.addAttribute(MODEL_IS_USER_AUTH, false);
            model.addAttribute(MODEL_LOGIN_REQUEST, new LoginRequest());
            return "login";
        }
        return "redirect:/main";
    }

    @GetMapping("/login")
    public String login(Model model) {
        if (!authService.isUserAuthorize()) {
            model.addAttribute(MODEL_LOGIN_REQUEST, new LoginRequest());
            model.addAttribute(MODEL_IS_USER_AUTH, true);
            return "login";
        }
        return "redirect:/main";
    }

    @GetMapping("/logout")
    public String logout() {
        authService.removeAuthorizedUser();
        return "redirect:/auth/login";
    }
}
