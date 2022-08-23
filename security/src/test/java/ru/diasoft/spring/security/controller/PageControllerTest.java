package ru.diasoft.spring.security.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PageControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    @DisplayName("Страница /authenticated доступна")
    void authenticatedPageIsAvailableTest() throws Exception {
        mvc.perform(get("/authenticated"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ROLE_UNKNOWN")
    @DisplayName("Страница /authenticated недоступна")
    void authenticatedPageIsNotAvailableTest() throws Exception {
        mvc.perform(get("/authenticated"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Страница /public доступна всем")
    void publicPageTest() throws Exception {
        mvc.perform(get("/public"))
                .andExpect(status().isOk());
    }
}