package ru.diasoft.spring.employeeservice.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final int USERNAME_MIN_SIZE = 8;
    public static final int USERNAME_MAX_SIZE = 50;
    public static final int PASSWORD_MIN_SIZE = 5;
    public static final int PASSWORD_MAX_SIZE = 20;
    public static final int FIRST_NAME_MAX_SIZE = 50;
    public static final int LAST_NAME_MAX_SIZE = 50;
    public static final int MIDDLE_NAME_MAX_SIZE = 50;

    public static final String EMPLOYEE_USERNAME_EXISTS = "Сотрудник с логином '%s' уже существует";
    public static final String TEAM_NAME_EXISTS = "Команда с именем '%s' уже существует";
    public static final String EMPLOYEE_IS_ALREADY_IN_TEAM = "Сотрудник '%s' уже в команде '%s'";
    public static final String EMPLOYEE_IS_NOT_IN_TEAM = "Сотрудник '%s' не в команде '%s'";
}
