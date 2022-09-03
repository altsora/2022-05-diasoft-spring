package ru.diasoft.spring.monitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.monitoring.domain.Book;

@Repository
@RepositoryRestResource(path = "book")
public interface BookRepository extends JpaRepository<Book, Integer> {

}
