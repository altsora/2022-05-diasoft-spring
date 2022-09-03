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
public class GetEmployeeByIdResponse extends BaseResponse {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("uniqNumber")
    private Integer uniqNumber;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("middleName")
    private String middleName;
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("active")
    private boolean active;
}
