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
public class AddTeamRequest {
    @NotBlank(message = "{validate.team.name.notBlank}")
    @Size(message = "{validate.team.name.size}",
            min = Constants.TEAM_NAME_MIN_SIZE,
            max = Constants.TEAM_NAME_MAX_SIZE)
    @JsonProperty("name")
    private String name;
}
