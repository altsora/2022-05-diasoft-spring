package ru.diasoft.spring.employeeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.employeeservice.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
