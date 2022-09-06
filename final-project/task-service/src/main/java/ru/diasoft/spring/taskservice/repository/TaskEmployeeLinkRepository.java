package ru.diasoft.spring.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.taskservice.domain.TaskEmployeeLink;

import java.util.List;

@Repository
public interface TaskEmployeeLinkRepository extends JpaRepository<TaskEmployeeLink, Integer> {
    @Query("SELECT te.taskId FROM TaskEmployeeLink te WHERE te.employeeId = :employeeId")
    List<Long> findAllTasksByEmployeeId(@Param("employeeId") Integer employeeId);
}
