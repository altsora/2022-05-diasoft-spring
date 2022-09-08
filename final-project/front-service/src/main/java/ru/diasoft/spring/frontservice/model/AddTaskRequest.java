package ru.diasoft.spring.frontservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddTaskRequest {
    @JsonProperty("title")
    private String title;
    @JsonProperty("meExecutor")
    private String meExecutor;
}
