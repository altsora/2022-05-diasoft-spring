package ru.diasoft.spring.studenttester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StudentTesterApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(StudentTesterApplication.class, args);
//		QuestionService questionService = context.getBean(QuestionService.class);
//		questionService.startTesting(new Scanner(System.in));
//		questionService.printAll();
		context.close();
	}

}
