package ru.diasoft.spring.booklibrarymvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.booklibrarymvc.domain.Author;

import java.util.List;

@Repository
@RepositoryRestResource(path = "author")
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
