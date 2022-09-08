package ru.diasoft.spring.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.taskservice.domain.Task;

import java.util.List;

@Repository
@RepositoryRestResource(path = "tasks")
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query("SELECT t " +
            "FROM TaskEmployeeLink te INNER JOIN FETCH Task t ON te.taskId = t.id " +
            "WHERE te.employeeId = :employeeId")
    List<Task> findAllTasksByEmployeeId(@Param("employeeId") Integer employeeId);

    @Query(nativeQuery = true,
            value = "SELECT t.* FROM t_tasks t " +
                    "WHERE NOT EXISTS (" +
                    "   SELECT 1 FROM t_task_employee_link te WHERE te.task_id = t.id" +
                    ")")
    List<Task> findAllTasksWithoutEmployee();
}
