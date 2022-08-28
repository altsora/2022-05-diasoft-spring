package ru.diasoft.spring.docker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.docker.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
