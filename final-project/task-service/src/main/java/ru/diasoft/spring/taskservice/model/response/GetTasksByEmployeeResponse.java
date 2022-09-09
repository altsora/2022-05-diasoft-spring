package ru.diasoft.spring.taskservice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.BaseResponse;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GetTasksByEmployeeResponse extends BaseResponse {
    @JsonProperty("employeeId")
    private Integer employeeId;
    @JsonProperty("tasks")
    private List<TaskForGetTasksByEmployeeResponse> tasks;
}
