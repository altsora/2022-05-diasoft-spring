package ru.diasoft.spring.booklibrarymvc.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.booklibrarymvc.domain.BookComment;

import java.util.List;

@Repository
@RepositoryRestResource(path = "book-comment")
public interface BookCommentRepository extends JpaRepository<BookComment, Long> {

    @EntityGraph(value = "graph.bookcomment-book")
    @Override
    List<BookComment> findAll();
}
