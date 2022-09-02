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
public class UpdateEmployeeRequest {
    @NotBlank(message = "{validate.employee.firstName.notBlank}")
    @Size(message = "{validate.employee.firstName.size}",
            max = Constants.FIRST_NAME_MAX_SIZE)
    @JsonProperty("firstName")
    private String firstName;

    @NotBlank(message = "{validate.employee.lastName.notBlank}")
    @Size(message = "{validate.employee.lastName.size}",
            max = Constants.LAST_NAME_MAX_SIZE)
    @JsonProperty("lastName")
    private String lastName;

    @NotBlank(message = "{validate.employee.middleName.notBlank}")
    @Size(message = "{validate.employee.middleName.size}",
            max = Constants.MIDDLE_NAME_MAX_SIZE)
    @JsonProperty("middleName")
    private String middleName;
}
