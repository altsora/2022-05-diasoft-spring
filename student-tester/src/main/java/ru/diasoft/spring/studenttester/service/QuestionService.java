package ru.diasoft.spring.studenttester.service;

import java.util.Scanner;

public interface QuestionService {
    void printAll();

    boolean startTesting(Scanner scanner);
}
