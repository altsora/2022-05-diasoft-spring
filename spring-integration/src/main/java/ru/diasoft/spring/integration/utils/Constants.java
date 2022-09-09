package ru.diasoft.spring.integration.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final String BEAN_NAME_JUNIOR_STUDY_SERVICE = "JuniorStudyService";
    public static final String BEAN_NAME_MIDDLE_STUDY_SERVICE = "MiddleStudyService";
    public static final String BEAN_NAME_SENIOR_STUDY_SERVICE = "SeniorStudyService";

    public static final String METHOD_NAME_STUDY = "study";

    public static final String PROGRAMMERS_CHANNEL = "programmersChanel";
    public static final String FINISH_STUDY_CHANNEL = "finishStudyChanel";

    public static final String AGGREGATE_FLOW_INPUT = "aggregateFlow.input";

    public static final int STUDY_DURATION_SECONDS = 1;
}
