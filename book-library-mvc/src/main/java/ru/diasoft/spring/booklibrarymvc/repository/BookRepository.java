package ru.diasoft.spring.booklibrarymvc.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.booklibrarymvc.domain.Book;

import java.util.List;

@Repository
@RepositoryRestResource(path = "book")
public interface BookRepository extends JpaRepository<Book, Long> {
    @EntityGraph("book-author")
    @Query("SELECT b FROM Book b JOIN FETCH b.genre")
    @Override
    List<Book> findAll();
}
