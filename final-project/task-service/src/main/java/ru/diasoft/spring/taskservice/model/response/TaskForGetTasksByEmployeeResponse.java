package ru.diasoft.spring.taskservice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskForGetTasksByEmployeeResponse {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("uniqNumber")
    private Integer uniqNumber;
    @JsonProperty("title")
    private String title;
    @JsonProperty("state")
    private String state;
}
