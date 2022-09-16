package ru.diasoft.spring.frontservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.diasoft.spring.frontservice.utils.FrontServiceConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddTaskRequest {
    @NotBlank(message = "{validate.task.title.notBlank}")
    @Size(message = "{validate.task.title.size}",
            min = FrontServiceConstants.TASK_TITLE_MIN_SIZE,
            max = FrontServiceConstants.TASK_TITLE_MAX_SIZE)
    @JsonProperty("title")
    private String title;

    @JsonProperty("meExecutor")
    private String meExecutor;
}
