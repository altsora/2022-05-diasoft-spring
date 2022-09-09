package ru.diasoft.spring.integration.service;

import ru.diasoft.spring.integration.domain.Grade;
import ru.diasoft.spring.integration.domain.Programmer;

public interface StudyService {
    Programmer study(Programmer programmer) throws Exception;

    Grade targetGrade();

    Grade lastGrade();

    default String programmerStartStudy(Programmer programmer) {
        return String.format("Программист %s (id = %s) учится на %s...",
                programmer.getName(), programmer.getId(), targetGrade());
    }

    default String programmerEndStudy(Programmer programmer) {
        return String.format("Программист %s (id = %s) стал %s!",
                programmer.getName(), programmer.getId(), targetGrade());
    }
}
