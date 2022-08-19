package ru.diasoft.spring.booklibrarymvc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("author")
    private AuthorDto author;
    @JsonProperty("genre")
    private GenreDto genre;
    @JsonProperty("comments")
    private List<BookCommentDto> comments;
}
