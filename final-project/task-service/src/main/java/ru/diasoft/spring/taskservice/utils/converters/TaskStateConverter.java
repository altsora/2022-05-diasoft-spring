package ru.diasoft.spring.taskservice.utils.converters;

import ru.diasoft.spring.taskservice.enums.TaskState;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class TaskStateConverter implements AttributeConverter<TaskState, String> {
    @Override
    public String convertToDatabaseColumn(TaskState attribute) {
        return attribute.getState();
    }

    @Override
    public TaskState convertToEntityAttribute(String dbData) {
        return TaskState.findByState(dbData)
                .orElseThrow(() -> new IllegalArgumentException("Неизвестное состояние задачи в базе: '" + dbData + "'"));
    }
}
