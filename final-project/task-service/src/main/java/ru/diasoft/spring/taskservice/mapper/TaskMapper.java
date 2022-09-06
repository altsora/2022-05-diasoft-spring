package ru.diasoft.spring.taskservice.mapper;

import org.mapstruct.Mapper;
import ru.diasoft.spring.taskservice.domain.Task;
import ru.diasoft.spring.taskservice.model.response.CreateTaskResponse;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    CreateTaskResponse fromDomainToCreateTaskResponse(Task task);
}
