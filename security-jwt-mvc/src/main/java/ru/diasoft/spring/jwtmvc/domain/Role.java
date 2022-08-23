package ru.diasoft.spring.jwtmvc.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@AllArgsConstructor
public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    GUEST("ROLE_GUEST");

    private final String authority;

    public SimpleGrantedAuthority getAuthorities() {
        return new SimpleGrantedAuthority(authority);
    }
}
