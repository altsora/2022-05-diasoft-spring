package ru.diasoft.spring.booklibrarymvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.booklibrarymvc.domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
