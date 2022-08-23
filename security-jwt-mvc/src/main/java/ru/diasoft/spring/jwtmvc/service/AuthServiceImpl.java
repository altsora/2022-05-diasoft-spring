package ru.diasoft.spring.jwtmvc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.diasoft.spring.jwtmvc.domain.User;
import ru.diasoft.spring.jwtmvc.model.LoginRequest;
import ru.diasoft.spring.jwtmvc.model.LoginResponse;
import ru.diasoft.spring.jwtmvc.security.JwtTokenUtil;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginResponse login(LoginRequest request) throws BadCredentialsException {
        final Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        final User user = (User) auth.getPrincipal();
        final String token = jwtTokenUtil.generateAccessToken(user);
        return LoginResponse.builder().username(user.getUsername()).token(token).build();
    }
}
