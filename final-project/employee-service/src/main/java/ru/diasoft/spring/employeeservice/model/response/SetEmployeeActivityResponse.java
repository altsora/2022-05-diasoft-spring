package ru.diasoft.spring.employeeservice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.BaseResponse;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetEmployeeActivityResponse extends BaseResponse {
    @JsonProperty("activity")
    private boolean activity;
}
