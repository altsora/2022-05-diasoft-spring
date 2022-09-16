package ru.diasoft.spring.frontservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.diasoft.spring.commonsspringbootauthoconfigure.aop.Loggable;
import ru.diasoft.spring.commonsspringbootauthoconfigure.feign.EmployeeServiceFeign;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.request.AddEmployeeRequest;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.request.LoginRequest;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.AddEmployeeResponse;
import ru.diasoft.spring.frontservice.model.RegistrationRequest;
import ru.diasoft.spring.frontservice.service.AuthService;

import javax.validation.Valid;
import java.util.Optional;

import static ru.diasoft.spring.frontservice.utils.FrontServiceConstants.*;

@Log4j2
@Loggable
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final EmployeeServiceFeign employeeServiceFeign;

    @GetMapping("/login")
    public String login(Model model) {
        if (authService.isNotUserAuthorize()) {
            model.addAttribute(MODEL_LOGIN_REQUEST, new LoginRequest());
            model.addAttribute(MODEL_IS_USER_AUTH, true);
            return PAGE_LOGIN;
        }
        return "redirect:/main";
    }

    @PostMapping("/login")
    public String loginForm(@ModelAttribute LoginRequest request, Model model) {
        final Optional<Integer> userId = authService.authorizeUser(request);
        if (userId.isEmpty()) {
            model.addAttribute(MODEL_IS_USER_AUTH, false);
            model.addAttribute(MODEL_LOGIN_REQUEST, new LoginRequest());
            return PAGE_LOGIN;
        }
        return "redirect:/main";
    }

    @GetMapping("/logout")
    public String logout() {
        authService.removeAuthorizedUser();
        return "redirect:/";
    }

    @GetMapping("/registration")
    public ModelAndView registrationPage(ModelAndView view) {
        view.addObject(MODEL_REGISTRATION_REQUEST, new RegistrationRequest());
        view.setViewName(PAGE_REGISTRATION);
        return view;
    }

    @PostMapping("/registration")
    public ModelAndView registrationAction(@ModelAttribute(MODEL_REGISTRATION_REQUEST) @Valid RegistrationRequest request,
                                     BindingResult result,
                                     ModelAndView view) {
        if (result.hasErrors()) {
            view.setViewName(PAGE_REGISTRATION);
            return view;
        }
        final AddEmployeeRequest addEmployeeRequest = AddEmployeeRequest.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .middleName(request.getMiddleName())
                .build();
        final AddEmployeeResponse response = employeeServiceFeign.addEmployee(addEmployeeRequest);
        if (response.isFail()) {
            view.addObject(MODEL_REGISTRATION_RESPONSE, response);
            view.setViewName(PAGE_REGISTRATION);
            return view;
        }
        view.setViewName("redirect:/auth/login");
        return view;
    }
}
