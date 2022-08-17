package ru.diasoft.spring.booklibrarymvc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddBookDto {
    @JsonProperty("title")
    private String title;
    @JsonProperty("authorId")
    private Long authorId;
    @JsonProperty("genreId")
    private Long genreId;
}
