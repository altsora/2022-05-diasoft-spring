package ru.diasoft.spring.studenttester.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionAnswer {
    private final int id;
    private final String question;
    private final String correctAnswer;
}
