package ru.diasoft.spring.jwtmvc.service;

import org.springframework.security.authentication.BadCredentialsException;
import ru.diasoft.spring.jwtmvc.model.LoginRequest;
import ru.diasoft.spring.jwtmvc.model.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request) throws BadCredentialsException;
}
