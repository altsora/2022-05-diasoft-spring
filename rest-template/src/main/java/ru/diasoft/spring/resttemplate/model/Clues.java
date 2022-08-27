package ru.diasoft.spring.resttemplate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Clues {
    private int id;
    private String answer;
    private String question;
    @JsonProperty("category_id")
    private int categoryId;
    private Category category;
}
