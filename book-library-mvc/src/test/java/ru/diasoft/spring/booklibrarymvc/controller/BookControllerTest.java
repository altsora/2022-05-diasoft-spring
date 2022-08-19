package ru.diasoft.spring.booklibrarymvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.diasoft.spring.booklibrarymvc.exception.DomainNotFoundException;
import ru.diasoft.spring.booklibrarymvc.model.*;
import ru.diasoft.spring.booklibrarymvc.service.BookService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@DisplayName("Тесты контроллера BookController")
class BookControllerTest {
    public static final String STRING_CONTENT_MEDIA_TYPE = "text/plain;charset=UTF-8";
    public static final String DOMAIN_NOT_FOUND_MSG = "notFound";
    public static final Long BOOK_ID = 10L;
    public static final String TITLE = "title";
    public static final AuthorDto AUTHOR_DTO = AuthorDto.builder()
            .id(1L)
            .firstName("firstName")
            .lastName("lastName")
            .build();
    public static final GenreDto GENRE_DTO = GenreDto.builder()
            .id(2L)
            .name("genreName")
            .build();
    public static final BookCommentDto COMMENT_DTO = BookCommentDto.builder()
            .id(3L)
            .text("comment")
            .build();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(bookService);
    }

    @Test
    @DisplayName("Найти книгу по её ID: книга найдена")
    void findBookByIdCorrectTest() throws Exception {
        final BookDto expected = BookDto.builder()
                .id(BOOK_ID)
                .title(TITLE)
                .author(AUTHOR_DTO)
                .genre(GENRE_DTO)
                .comments(Collections.singletonList(COMMENT_DTO))
                .build();

        doReturn(expected).when(bookService).getById(BOOK_ID);

        mvc.perform(get("/books/{id}", BOOK_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(expected)))
                .andExpect(jsonPath("$.id", is(BOOK_ID.intValue())))
                .andExpect(jsonPath("$.title", is(TITLE)))
                .andExpect(jsonPath("$.author.id", is(AUTHOR_DTO.getId().intValue())))
                .andExpect(jsonPath("$.author.firstName", is(AUTHOR_DTO.getFirstName())))
                .andExpect(jsonPath("$.author.lastName", is(AUTHOR_DTO.getLastName())))
                .andExpect(jsonPath("$.genre.id", is(GENRE_DTO.getId().intValue())))
                .andExpect(jsonPath("$.genre.name", is(GENRE_DTO.getName())))
                .andExpect(jsonPath("$.comments[0].id", is(COMMENT_DTO.getId().intValue())))
                .andExpect(jsonPath("$.comments[0].text", is(COMMENT_DTO.getText())));

        verify(bookService).getById(BOOK_ID);
    }

    @Test
    @DisplayName("Найти книгу по её ID: книга не найдена, выброс ошибки")
    void findBookByIdErrorTest() throws Exception {
        final DomainNotFoundException exceptedException = new DomainNotFoundException(DOMAIN_NOT_FOUND_MSG);

        doThrow(exceptedException).when(bookService).getById(BOOK_ID);

        mvc.perform(get("/books/{id}", BOOK_ID))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(STRING_CONTENT_MEDIA_TYPE))
                .andExpect(content().string(exceptedException.getMessage()));

        verify(bookService).getById(BOOK_ID);
    }

    @Test
    @DisplayName("Получить все книги")
    void findAllBookCorrectTest() throws Exception {
        final BookDto dto1 = BookDto.builder()
                .id(1L)
                .title(TITLE + "1")
                .author(AUTHOR_DTO)
                .genre(GENRE_DTO)
                .comments(Collections.emptyList())
                .build();
        final BookDto dto2 = BookDto.builder()
                .id(2L)
                .title(TITLE + "2")
                .author(AUTHOR_DTO)
                .genre(GENRE_DTO)
                .comments(Collections.emptyList())
                .build();
        final List<BookDto> expected = Arrays.asList(dto1, dto2);

        doReturn(expected).when(bookService).getAll();

        mvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(expected)))
                .andExpect(jsonPath("$[0].id", is(dto1.getId().intValue())))
                .andExpect(jsonPath("$[0].title", is(dto1.getTitle())))
                .andExpect(jsonPath("$[0].author.id", is(dto1.getAuthor().getId().intValue())))
                .andExpect(jsonPath("$[0].author.firstName", is(dto1.getAuthor().getFirstName())))
                .andExpect(jsonPath("$[0].author.lastName", is(dto1.getAuthor().getLastName())))
                .andExpect(jsonPath("$[0].genre.id", is(dto1.getGenre().getId().intValue())))
                .andExpect(jsonPath("$[0].genre.name", is(dto1.getGenre().getName())))
                .andExpect(jsonPath("$[0].comments", is(Collections.emptyList())))

                .andExpect(jsonPath("$[1].id", is(dto2.getId().intValue())))
                .andExpect(jsonPath("$[1].title", is(dto2.getTitle())))
                .andExpect(jsonPath("$[1].author.id", is(dto2.getAuthor().getId().intValue())))
                .andExpect(jsonPath("$[1].author.firstName", is(dto2.getAuthor().getFirstName())))
                .andExpect(jsonPath("$[1].author.lastName", is(dto2.getAuthor().getLastName())))
                .andExpect(jsonPath("$[1].genre.id", is(dto2.getGenre().getId().intValue())))
                .andExpect(jsonPath("$[1].genre.name", is(dto2.getGenre().getName())))
                .andExpect(jsonPath("$[1].comments", is(Collections.emptyList())));

        verify(bookService).getAll();
    }

    @Test
    @DisplayName("Добавление книги: успешно")
    void addBookCorrectTest() throws Exception {
        final AddBookDto request = AddBookDto.builder()
                .title(TITLE)
                .authorId(AUTHOR_DTO.getId())
                .genreId(GENRE_DTO.getId())
                .build();
        final BookDto expected = BookDto.builder()
                .id(BOOK_ID)
                .title(TITLE)
                .author(AUTHOR_DTO)
                .genre(GENRE_DTO)
                .build();

        doReturn(expected).when(bookService).addBook(request);

        mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(expected)));

        verify(bookService).addBook(request);
    }

    @Test
    @DisplayName("Добавление книги: ошибка, сущность не найдена")
    void addBookErrorDomainNotFoundTest() throws Exception {
        final AddBookDto request = AddBookDto.builder()
                .title(TITLE)
                .authorId(AUTHOR_DTO.getId())
                .genreId(GENRE_DTO.getId())
                .build();
        final DomainNotFoundException exceptedException = new DomainNotFoundException(DOMAIN_NOT_FOUND_MSG);

        doThrow(exceptedException).when(bookService).addBook(request);

        mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(STRING_CONTENT_MEDIA_TYPE))
                .andExpect(content().string(exceptedException.getMessage()));

        verify(bookService).addBook(request);
    }

    @Test
    @DisplayName("Обновление книги: успешно")
    void updateBookCorrectTest() throws Exception {
        final UpdateBookDto request = UpdateBookDto.builder()
                .title(TITLE)
                .authorId(AUTHOR_DTO.getId())
                .genreId(GENRE_DTO.getId())
                .build();
        final BookDto expected = BookDto.builder()
                .id(BOOK_ID)
                .title(TITLE)
                .author(AUTHOR_DTO)
                .genre(GENRE_DTO)
                .build();

        doReturn(expected).when(bookService).updateBook(BOOK_ID, request);

        mvc.perform(put("/books/{id}", BOOK_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(expected)));

        verify(bookService).updateBook(BOOK_ID, request);
    }

    @Test
    @DisplayName("Обновление книги: ошибка, сущность не найдена")
    void updateBookErrorDomainNotFoundTest() throws Exception {
        final UpdateBookDto request = UpdateBookDto.builder()
                .title(TITLE)
                .authorId(AUTHOR_DTO.getId())
                .genreId(GENRE_DTO.getId())
                .build();
        final DomainNotFoundException exceptedException = new DomainNotFoundException(DOMAIN_NOT_FOUND_MSG);

        doThrow(exceptedException).when(bookService).updateBook(BOOK_ID, request);

        mvc.perform(put("/books/{id}", BOOK_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(STRING_CONTENT_MEDIA_TYPE))
                .andExpect(content().string(exceptedException.getMessage()));

        verify(bookService).updateBook(BOOK_ID, request);
    }

    @Test
    @DisplayName("Удаление книги: успешно")
    void deleteBookCorrectTest() throws Exception {
        doNothing().when(bookService).deleteBookById(BOOK_ID);

        mvc.perform(delete("/books/{id}", BOOK_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(bookService).deleteBookById(BOOK_ID);
    }
}