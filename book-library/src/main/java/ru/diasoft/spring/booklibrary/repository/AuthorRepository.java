package ru.diasoft.spring.booklibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.booklibrary.domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
