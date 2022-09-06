package ru.diasoft.spring.taskservice.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TaskServiceConstants {
    public static final String TASK_STATE_NOT_FOUND = "Неизвестное состояние задачи: '%s'";
    public static final String EMPLOYEE_HAS_NO_TASKS = "Сотрудник с ID '%s' не имеет задач";
}
