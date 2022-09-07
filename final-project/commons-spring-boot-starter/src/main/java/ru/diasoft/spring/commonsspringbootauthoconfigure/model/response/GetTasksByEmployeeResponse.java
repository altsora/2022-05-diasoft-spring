package ru.diasoft.spring.commonsspringbootauthoconfigure.model.response;

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
public class GetTasksByEmployeeResponse extends BaseResponse {
    @JsonProperty("employeeId")
    private Integer employeeId;
    @JsonProperty("tasks")
    private List<TaskForGetTasksByEmployeeResponse> tasks;
}
