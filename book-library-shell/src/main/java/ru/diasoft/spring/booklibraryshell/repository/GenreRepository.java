package ru.diasoft.spring.booklibraryshell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.booklibraryshell.domain.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

}
