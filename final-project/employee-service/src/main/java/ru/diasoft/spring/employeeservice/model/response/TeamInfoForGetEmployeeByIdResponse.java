package ru.diasoft.spring.employeeservice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamInfoForGetEmployeeByIdResponse {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("uniqNumber")
    private Integer uniqNumber;
    @JsonProperty("name")
    private String name;
    @JsonProperty("active")
    private boolean active;
}
