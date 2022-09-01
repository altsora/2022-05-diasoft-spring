package ru.diasoft.spring.jwtmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.diasoft.spring.jwtmvc.model.AddBookDto;
import ru.diasoft.spring.jwtmvc.model.UpdateBookDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("classpath:data-test.sql")
class BookControllerTest {
    private static final String USER_DETAILS_SERVICE_BEAN_NAME = "userService";
    private static final String USER_WITH_ROLE_GUEST = "user_guest";
    private static final String USER_WITH_ROLE_USER = "user_user";
    private static final String USER_WITH_ROLE_ADMIN = "user_admin";
    private static final Integer BOOK_ID = 1;
    private static final AddBookDto ADD_BOOK_DTO_REQUEST = AddBookDto.builder()
            .title("title")
            .build();
    private static final UpdateBookDto UPDATE_BOOK_DTO_REQUEST = UpdateBookDto.builder()
            .title("title")
            .build();

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithUserDetails(
            value = USER_WITH_ROLE_GUEST,
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME
    )
    void getRandomBookTest_roleGuest() throws Exception {
        mvc.perform(get("/books/rand"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(
            value = USER_WITH_ROLE_USER,
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME
    )
    void getRandomBookTest_roleUser() throws Exception {
        mvc.perform(get("/books/rand"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(
            value = USER_WITH_ROLE_ADMIN,
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME
    )
    void getRandomBookTest_roleAdmin() throws Exception {
        mvc.perform(get("/books/rand"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(
            value = USER_WITH_ROLE_GUEST,
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME
    )
    void getAllBooks_roleGuest() throws Exception {
        mvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(
            value = USER_WITH_ROLE_USER,
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME
    )
    void getAllBooks_roleUser() throws Exception {
        mvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(
            value = USER_WITH_ROLE_ADMIN,
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME
    )
    void getAllBooks_roleAdmin() throws Exception {
        mvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(
            value = USER_WITH_ROLE_GUEST,
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME
    )
    void getBookById_roleGuest() throws Exception {
        mvc.perform(get("/books/{id}", BOOK_ID))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(
            value = USER_WITH_ROLE_USER,
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME
    )
    void getBookById_roleUser() throws Exception {
        mvc.perform(get("/books/{id}", BOOK_ID))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(
            value = USER_WITH_ROLE_ADMIN,
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME
    )
    void getBookById_roleAdmin() throws Exception {
        mvc.perform(get("/books/{id}", BOOK_ID))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(
            value = USER_WITH_ROLE_GUEST,
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME
    )
    void addBook_roleGuest_forbidden() throws Exception {
        mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ADD_BOOK_DTO_REQUEST)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(
            value = USER_WITH_ROLE_USER,
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME
    )
    void addBook_roleUser() throws Exception {
        mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ADD_BOOK_DTO_REQUEST)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithUserDetails(
            value = USER_WITH_ROLE_ADMIN,
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME
    )
    void addBook_roleAdmin() throws Exception {
        mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ADD_BOOK_DTO_REQUEST)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithUserDetails(
            value = USER_WITH_ROLE_GUEST,
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME
    )
    void updateBook_roleGuest_forbidden() throws Exception {
        mvc.perform(put("/books/{id}", BOOK_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(UPDATE_BOOK_DTO_REQUEST)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(
            value = USER_WITH_ROLE_USER,
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME
    )
    void updateBook_roleUser() throws Exception {
        mvc.perform(put("/books/{id}", BOOK_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(UPDATE_BOOK_DTO_REQUEST)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(
            value = USER_WITH_ROLE_ADMIN,
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME
    )
    void updateBook_roleAdmin() throws Exception {
        mvc.perform(put("/books/{id}", BOOK_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(UPDATE_BOOK_DTO_REQUEST)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(
            value = USER_WITH_ROLE_GUEST,
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME
    )
    void deleteBook_roleGuest_forbidden() throws Exception {
        mvc.perform(delete("/books/{id}", BOOK_ID))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(
            value = USER_WITH_ROLE_USER,
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME
    )
    void deleteBook_roleUser_forbidden() throws Exception {
        mvc.perform(delete("/books/{id}", BOOK_ID))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(
            value = USER_WITH_ROLE_ADMIN,
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME
    )
    void deleteBook_roleAdmin() throws Exception {
        mvc.perform(delete("/books/{id}", BOOK_ID))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //    @Test
//    @WithUserDetails(
//            value = "user_guest",
//            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME
//    )
//    void name() throws Exception {
//        mvc.perform(delete("/books/{id}", 1))
//                .andDo(print())
//                .andExpect(status().isForbidden());
//    }
}