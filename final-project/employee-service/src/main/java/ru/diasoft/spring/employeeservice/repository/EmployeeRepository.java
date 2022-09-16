package ru.diasoft.spring.employeeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.spring.employeeservice.domain.Employee;

import java.util.Optional;

@Repository
@RestResource(path = "employees")
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Employee e SET e.active = :value WHERE e.id = :id")
    void setActivity(@Param("id") Integer employeeId, @Param("value") boolean value);

    boolean existsByUsername(String username);

    @Query("SELECT e.id FROM Employee e WHERE e.username = :username AND e.password = :password")
    Optional<Integer> findIdByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
