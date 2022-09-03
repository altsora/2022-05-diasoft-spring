package ru.diasoft.spring.employeeservice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.diasoft.spring.commonsspringbootauthoconfigure.utils.BaseResponse;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetActivityResponse extends BaseResponse {
    @JsonProperty("activity")
    private boolean activity;
}
