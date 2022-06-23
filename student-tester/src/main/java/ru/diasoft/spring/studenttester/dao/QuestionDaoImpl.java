package ru.diasoft.spring.studenttester.dao;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.diasoft.spring.studenttester.domain.QuestionAnswer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Component
public class QuestionDaoImpl implements QuestionDao {
    private final List<QuestionAnswer> questions;

    @Autowired
    public QuestionDaoImpl(@Value("${testing.file.name}") String fileName, @Value("${testing.file.separator}") String sep) {
        Objects.requireNonNull(fileName, "file with questions is null");
        Objects.requireNonNull(sep, "separator is null");
        this.questions = new ArrayList<>();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
            Objects.requireNonNull(is, "InputStream with file " + fileName + " is null");
            final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String header = reader.readLine();
            String row;
            while ((row = reader.readLine()) != null) {
                final String[] columns = row.split(sep);
                final QuestionAnswer questionAnswer = QuestionAnswer.builder()
                        .id(Integer.parseInt(columns[0]))
                        .question(columns[1])
                        .correctAnswer(columns[2])
                        .build();
                questions.add(questionAnswer);
            }
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    @Override
    public Optional<QuestionAnswer> findById(int id) {
        return questions.stream()
                .filter(q -> q.getId() == id)
                .findFirst();
    }

    @Override
    public List<QuestionAnswer> findAll() {
        return questions;
    }

    @Override
    public List<QuestionAnswer> getRandomQuestions(int count) {
        final List<QuestionAnswer> list = new ArrayList<>(questions);
        Collections.shuffle(list);
        return list.stream().limit(count).collect(Collectors.toList());
    }
}
