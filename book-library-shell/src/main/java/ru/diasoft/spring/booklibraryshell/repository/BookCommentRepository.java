package ru.diasoft.spring.booklibraryshell.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.booklibraryshell.domain.BookComment;

import java.util.List;

@Repository
public interface BookCommentRepository extends JpaRepository<BookComment, Long> {

    @EntityGraph(value = "graph.bookcomment-book")
    @Override
    List<BookComment> findAll();
}
