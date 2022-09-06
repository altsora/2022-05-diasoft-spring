package ru.diasoft.spring.frontservice.service;

import ru.diasoft.spring.commonsspringbootauthoconfigure.model.request.LoginRequest;

import java.util.Optional;

public interface AuthService {
    boolean isUserAuthorize();

    Integer getAuthorizedUserId();

    Optional<Integer> authorizeUser(LoginRequest request);

    void removeAuthorizedUser();
}
