package ru.diasoft.spring.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.spring.taskservice.domain.TaskEmployeeLink;

@Repository
public interface TaskEmployeeLinkRepository extends JpaRepository<TaskEmployeeLink, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM TaskEmployeeLink te WHERE te.taskId = :taskId")
    void deleteAllByTaskId(@Param("taskId") Integer taskId);
}
