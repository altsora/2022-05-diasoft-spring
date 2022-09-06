package ru.diasoft.spring.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.spring.taskservice.domain.TaskEmployeeLink;

import java.util.Optional;

@Repository
public interface TaskEmployeeLinkRepository extends JpaRepository<TaskEmployeeLink, Integer> {
    @Query("SELECT te.employeeId FROM TaskEmployeeLink te WHERE te.taskId = :taskId")
    Optional<Integer> findEmployeeIdByTaskId(@Param("taskId") Integer taskId);

    @Modifying
    @Transactional
    @Query("DELETE FROM TaskEmployeeLink te WHERE te.taskId = :taskId AND te.employeeId = :employeeId")
    void deleteByTaskAndEmployee(@Param("taskId") Integer taskId, @Param("employeeId") Integer employeeId);
}
