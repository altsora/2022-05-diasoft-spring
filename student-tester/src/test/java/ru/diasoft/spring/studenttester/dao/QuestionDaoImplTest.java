package ru.diasoft.spring.studenttester.dao;

import org.assertj.core.data.Index;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.diasoft.spring.studenttester.domain.QuestionAnswer;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Юнит-тесты класса QuestionDaoImplTest")
class QuestionDaoImplTest {
    private final QuestionDao dao = new QuestionDaoImpl("questions-test.csv", ";");

    @Test
    @DisplayName("Метод возвращает все вопросы")
    void findAllTest() {
        final List<QuestionAnswer> actual = dao.findAll();
        assertThat(actual)
                .isNotNull()
                .hasSize(5)
                .contains(questionAnswer(1, "Question1", "Answer1"), Index.atIndex(0))
                .contains(questionAnswer(2, "Question2", "Answer2"), Index.atIndex(1))
                .contains(questionAnswer(3, "Question3", "Answer3"), Index.atIndex(2))
                .contains(questionAnswer(4, "Question4", "Answer4"), Index.atIndex(3))
                .contains(questionAnswer(5, "Question5", "Answer5"), Index.atIndex(4));
    }

    private QuestionAnswer questionAnswer(int id, String question, String correctAnswer) {
        return QuestionAnswer.builder().id(id).question(question).correctAnswer(correctAnswer).build();
    }

    @Test
    @DisplayName("Метод возвращает три случайных вопроса")
    void getRandomQuestionsTest() {
        final List<QuestionAnswer> actual = dao.getRandomQuestions(3);
        assertThat(actual)
                .isNotNull()
                .hasSize(3);
    }

    @Test
    @DisplayName("Поиск вопроса по ID")
    void findByIdTest() {
        final Optional<QuestionAnswer> actual = dao.findById(1);
        assertNotNull(actual);
        assertTrue(actual.isPresent());
        final QuestionAnswer questionAnswer = actual.get();
        assertEquals(1, questionAnswer.getId());
    }
}