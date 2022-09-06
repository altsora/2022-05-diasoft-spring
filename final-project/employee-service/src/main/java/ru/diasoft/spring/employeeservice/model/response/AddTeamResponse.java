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
public class AddTeamResponse extends BaseResponse {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("uniqNumber")
    private String uniqNumber;
    @JsonProperty("name")
    private String name;
}
