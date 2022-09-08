package ru.diasoft.spring.employeeservice.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.diasoft.spring.employeeservice.utils.Constants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "{validate.employee.username.notBlank}")
    @Size(message = "{validate.employee.username.size}",
            min = Constants.USERNAME_MIN_SIZE,
            max = Constants.USERNAME_MAX_SIZE)
    @JsonProperty("username")
    private String username;

    @NotBlank(message = "{validate.employee.password.notBlank}")
    @Size(message = "{validate.employee.password.size}",
            min = Constants.PASSWORD_MIN_SIZE,
            max = Constants.PASSWORD_MAX_SIZE)
    @JsonProperty("password")
    private String password;
}
