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
public class GenreDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
}
