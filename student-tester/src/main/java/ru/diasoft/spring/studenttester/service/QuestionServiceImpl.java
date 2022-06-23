package ru.diasoft.spring.studenttester.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.diasoft.spring.studenttester.dao.QuestionDao;
import ru.diasoft.spring.studenttester.domain.QuestionAnswer;

import java.util.List;
import java.util.Scanner;

@Log4j2
@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;
    private final MessageService messageService;
    private final int minQuestions;
    private final int minCorrectAnswers;

    @Autowired
    public QuestionServiceImpl(QuestionDao questionDao,
                               MessageService messageService,
                               @Value("${testing.min-questions}") int minQuestions,
                               @Value("${testing.min-correct-answers}") int minCorrectAnswers) {
        this.questionDao = questionDao;
        this.messageService = messageService;
        this.minQuestions = minQuestions;
        this.minCorrectAnswers = minCorrectAnswers;
    }

    @Override
    public void printAll() {
        questionDao.findAll().forEach(q -> System.out.println(messageService.getMessage("print-all",
                q.getId(),
                q.getQuestion(),
                q.getCorrectAnswer())));
    }

    @Override
    public boolean startTesting(Scanner scanner) {
        int correctAnswers = 0;
        final List<QuestionAnswer> questions = questionDao.getRandomQuestions(minQuestions);
        for (int i = 0; i < questions.size(); i++) {
            final QuestionAnswer question = questions.get(i);
            System.out.println(messageService.getMessage("test.question", (i + 1), question.getQuestion()));
            System.out.print(messageService.getMessage("test.answer"));
            final String userAnswer = scanner.nextLine();
            if (question.getCorrectAnswer().equalsIgnoreCase(userAnswer)) {
                correctAnswers++;
            }
        }
        final boolean testIsPassed = correctAnswers >= minCorrectAnswers;
        System.out.println("\n" + messageService.getMessage("test.result.info", correctAnswers, questions.size()));
        System.out.println(messageService.getMessage(testIsPassed ? "test.result.win" : "test.result.fail"));
        return testIsPassed;
    }
}
