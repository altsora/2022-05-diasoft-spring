package ru.diasoft.spring.taskservice.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TaskServiceConstants {
    public static final String TASK_STATE_NOT_FOUND = "Неизвестное состояние задачи: '%s'";
    public static final String EMPLOYEE_IS_EXECUTOR_OF_TASK = "За Вами закреплена задача №'%s' под названием '%s'";
    public static final String EMPLOYEE_IS_NOT_EXECUTOR_OF_TASK = "Задача №'%s' под названием '%s' снята с Вас";
    public static final String TASK_HAS_NO_ALREADY_EXECUTOR = "На задаче с ID '%s' уже нет исполнителя";
    public static final String EMPLOYEE_BY_ID_NOT_FOUND = "Сотрудник с ID '%s' не найден";
    public static final String TASK_HAS_ALREADY_EXECUTOR = "На задаче с ID '%s' уже установлен исполнитель с ID '%s'";
}
