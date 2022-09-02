package ru.diasoft.spring.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.taskservice.domain.Task;

@Repository
@RepositoryRestResource(path = "tasks")
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
