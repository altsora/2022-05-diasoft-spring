package ru.diasoft.spring.frontservice.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FrontServiceConstants {
    public static final String MODEL_FULL_NAME = "fullName";
    public static final String MODEL_LOGIN_REQUEST = "loginRequest";
    public static final String MODEL_REGISTRATION_REQUEST = "registrationRequest";
    public static final String MODEL_REGISTRATION_RESPONSE = "registrationResponse";
    public static final String MODEL_IS_USER_AUTH = "isUserAuth";
    public static final String MODEL_TASKS = "tasks";
    public static final String MODEL_AVAILABLE_TASKS = "availableTasks";
    public static final String MODEL_ADD_TASK_REQUEST = "addTaskRequest";
    public static final String MODEL_ADD_TASK_RESPONSE = "addTaskResponse";
    public static final String MODEL_SET_EXECUTOR_RESPONSE = "setExecutorResponse";

    public static final String PAGE_INDEX = "index";
    public static final String PAGE_MAIN = "main";
    public static final String PAGE_LOGIN = "login";
    public static final String PAGE_ADD_TASK = "add-task";
    public static final String PAGE_TAKE_TASK = "take-task";
    public static final String PAGE_REGISTRATION = "registration";

    public static final int USERNAME_MIN_SIZE = 8;
    public static final int USERNAME_MAX_SIZE = 50;
    public static final int PASSWORD_MIN_SIZE = 5;
    public static final int PASSWORD_MAX_SIZE = 20;
    public static final int FIRST_NAME_MAX_SIZE = 50;
    public static final int LAST_NAME_MAX_SIZE = 50;
    public static final int MIDDLE_NAME_MAX_SIZE = 50;
}
