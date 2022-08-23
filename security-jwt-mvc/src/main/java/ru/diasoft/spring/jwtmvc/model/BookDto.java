package ru.diasoft.spring.jwtmvc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title")
    private String title;
}
