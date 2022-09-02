package ru.diasoft.spring.employeeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.spring.employeeservice.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Employee e SET e.active = :value WHERE e.id = :id")
    void setActivity(@Param("id") Integer employeeId, @Param("value") boolean value);

    boolean existsByUsername(String username);
}
