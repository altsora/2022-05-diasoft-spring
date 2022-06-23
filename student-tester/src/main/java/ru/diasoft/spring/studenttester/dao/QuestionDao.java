package ru.diasoft.spring.studenttester.dao;


import ru.diasoft.spring.studenttester.domain.QuestionAnswer;

import java.util.List;
import java.util.Optional;

public interface QuestionDao {
    Optional<QuestionAnswer> findById(int id);

    List<QuestionAnswer> findAll();

    List<QuestionAnswer> getRandomQuestions(int count);
}
