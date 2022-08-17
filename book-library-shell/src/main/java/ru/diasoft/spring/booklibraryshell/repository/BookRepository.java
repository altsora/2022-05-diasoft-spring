package ru.diasoft.spring.booklibraryshell.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.booklibraryshell.domain.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @EntityGraph("book-author")
    @Query("SELECT b FROM Book b JOIN FETCH b.genre")
    @Override
    List<Book> findAll();

    List<Book> findAllByGenreName(String genreName);

    List<Book> findAllByAuthorId(Long authorId);
}
