package ru.diasoft.spring.kafkaservice.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeNotificationPayload {
    @JsonProperty("employeeId")
    private Integer employeeId;
    @JsonProperty("taskId")
    private Integer taskId;
    @JsonProperty("message")
    private String message;
}
