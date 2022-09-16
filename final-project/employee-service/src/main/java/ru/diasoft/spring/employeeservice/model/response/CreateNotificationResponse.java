package ru.diasoft.spring.employeeservice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.BaseResponse;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationResponse extends BaseResponse {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("uniqNumber")
    private Integer uniqNumber;
    @JsonProperty("employeeId")
    private Integer employeeId;
    @JsonProperty("dateTime")
    private LocalDateTime dateTime;
    @JsonProperty("message")
    private String message;
}
