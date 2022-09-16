package ru.diasoft.spring.taskservice.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.diasoft.spring.taskservice.utils.TaskServiceConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskRequest {
    @NotBlank(message = "{validate.task.title.notBlank}")
    @Size(message = "{validate.task.title.size}",
            min = TaskServiceConstants.TASK_TITLE_MIN_SIZE,
            max = TaskServiceConstants.TASK_TITLE_MAX_SIZE)
    @JsonProperty("title")
    private String title;
}
