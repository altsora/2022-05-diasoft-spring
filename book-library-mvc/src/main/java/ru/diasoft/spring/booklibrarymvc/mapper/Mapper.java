package ru.diasoft.spring.booklibrarymvc.mapper;

import lombok.experimental.UtilityClass;
import ru.diasoft.spring.booklibrarymvc.domain.Author;
import ru.diasoft.spring.booklibrarymvc.domain.Book;
import ru.diasoft.spring.booklibrarymvc.domain.BookComment;
import ru.diasoft.spring.booklibrarymvc.domain.Genre;
import ru.diasoft.spring.booklibrarymvc.model.AuthorDto;
import ru.diasoft.spring.booklibrarymvc.model.BookCommentDto;
import ru.diasoft.spring.booklibrarymvc.model.BookDto;
import ru.diasoft.spring.booklibrarymvc.model.GenreDto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class Mapper {
    public static AuthorDto toDto(Author domain) {
        return AuthorDto.builder()
                .id(domain.getId())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .build();
    }

    public static Author toDomain(AuthorDto dto) {
        return Author.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();
    }

    public static GenreDto toDto(Genre domain) {
        return GenreDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .build();
    }

    public static Genre toDomain(GenreDto dto) {
        return Genre.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    public static List<BookCommentDto> toDto(List<BookComment> comments) {
        if (comments == null)
            return Collections.emptyList();
        return comments.stream()
                .map(domain -> BookCommentDto.builder()
                        .id(domain.getId())
                        .text(domain.getText())
                        .build())
                .collect(Collectors.toList());
    }

    public static BookDto toDto(Book domain) {
        return BookDto.builder()
                .id(domain.getId())
                .title(domain.getTitle())
                .author(toDto(domain.getAuthor()))
                .genre(toDto(domain.getGenre()))
                .comments(toDto(domain.getComments()))
                .build();
    }
}
