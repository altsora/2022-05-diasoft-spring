package ru.diasoft.spring.jwtmvc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtMvcApplicationTest {
    @Test
    @DisplayName("Загрузка контекста")
    void loadContext() {
        assertTrue(true);
    }
}