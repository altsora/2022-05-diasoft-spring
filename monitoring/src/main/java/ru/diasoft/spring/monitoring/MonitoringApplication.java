package ru.diasoft.spring.monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.diasoft.spring.monitoring.domain.Book;
import ru.diasoft.spring.monitoring.repository.BookRepository;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class MonitoringApplication {
    @Autowired
    private BookRepository bookRepository;

    @PostConstruct
    private void init() {
        bookRepository.save(Book.builder().title("book1").build());
    }

    public static void main(String[] args) {
        SpringApplication.run(MonitoringApplication.class, args);
    }
}
