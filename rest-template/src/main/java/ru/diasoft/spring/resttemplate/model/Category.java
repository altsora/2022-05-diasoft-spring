package ru.diasoft.spring.resttemplate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Category {
    private int id;
    private String title;
    @JsonProperty("clues_count")
    private int cluesCount;
}
