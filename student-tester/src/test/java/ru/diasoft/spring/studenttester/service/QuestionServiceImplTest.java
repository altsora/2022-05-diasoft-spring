package ru.diasoft.spring.studenttester.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.diasoft.spring.studenttester.dao.QuestionDao;
import ru.diasoft.spring.studenttester.domain.QuestionAnswer;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@DisplayName("Юнит-тесты класса QuestionServiceImplTest")
class QuestionServiceImplTest {

    @Test
    @DisplayName("Вывод всех вопросов")
    void printAllTest() {
        final QuestionDao questionDao = mock(QuestionDao.class);
        final MessageService messageService = mock(MessageService.class);
        final QuestionService questionService = new QuestionServiceImpl(questionDao, messageService, -1, -1);

        questionService.printAll();

        verify(questionDao, times(1)).findAll();
    }

    @Test
    @DisplayName("Тестирование студента: тест пройден")
    void startTestingTest_studentTestIsPassed() {
        final int minQuestions = 2;
        final int minCorrectAnswers = 1;
        final QuestionDao questionDao = mock(QuestionDao.class);
        final MessageService messageService = mock(MessageService.class);
        final QuestionService questionService = new QuestionServiceImpl(questionDao, messageService, minQuestions, minCorrectAnswers);
        final Scanner scanner = new Scanner(
                "a1\n" +
                        "a2\n"
        );
        final List<QuestionAnswer> questions = Arrays.asList(
                questionAnswer("q1", "a1"),
                questionAnswer("q2", "a2")
        );

        doReturn(questions).when(questionDao).getRandomQuestions(minQuestions);

        boolean actual = questionService.startTesting(scanner);
        assertTrue(actual);

        verify(questionDao, times(1)).getRandomQuestions(minQuestions);
    }

    @Test
    @DisplayName("Тестирование студента: тест пройден")
    void startTestingTest_studentTestIsFailed() {
        final int minQuestions = 2;
        final int minCorrectAnswers = 1;
        final QuestionDao questionDao = mock(QuestionDao.class);
        final MessageService messageService = mock(MessageService.class);
        final QuestionService questionService = new QuestionServiceImpl(questionDao, messageService, minQuestions, minCorrectAnswers);
        final Scanner scanner = new Scanner("Alex\n" +
                "Bob\n" +
                "incorrectAnswer\n" +
                "incorrectAnswer\n"
        );
        final List<QuestionAnswer> questions = Arrays.asList(
                questionAnswer("q1", "a1"),
                questionAnswer("q2", "a2")
        );

        doReturn(questions).when(questionDao).getRandomQuestions(minQuestions);

        boolean actual = questionService.startTesting(scanner);
        assertFalse(actual);

        verify(questionDao, times(1)).getRandomQuestions(minQuestions);
    }

    private QuestionAnswer questionAnswer(String question, String correctAnswer) {
        return QuestionAnswer.builder().id(0).question(question).correctAnswer(correctAnswer).build();
    }
}