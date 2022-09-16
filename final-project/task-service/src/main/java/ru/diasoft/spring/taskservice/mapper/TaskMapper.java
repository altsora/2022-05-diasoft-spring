package ru.diasoft.spring.taskservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.diasoft.spring.taskservice.domain.Task;
import ru.diasoft.spring.taskservice.model.response.CreateTaskResponse;
import ru.diasoft.spring.taskservice.model.response.TaskForGetTasksByEmployeeResponse;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    CreateTaskResponse fromDomainToCreateTaskResponse(Task domain);

    @Mapping(target = "state", expression = "java(domain.getState().getState())")
    TaskForGetTasksByEmployeeResponse fromDomainToTaskForGetTasksByEmployeeResponse(Task domain);
}
