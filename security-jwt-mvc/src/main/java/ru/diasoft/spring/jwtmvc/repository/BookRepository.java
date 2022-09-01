package ru.diasoft.spring.jwtmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.jwtmvc.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
