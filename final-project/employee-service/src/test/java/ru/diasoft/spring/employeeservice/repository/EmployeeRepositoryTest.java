package ru.diasoft.spring.employeeservice.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.diasoft.spring.employeeservice.domain.Employee;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Тесты репозитория сотрудников EmployeeRepository")
class EmployeeRepositoryTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private EmployeeRepository repo;

    @Test
    @DisplayName("Сохранение сотрудника")
    void saveTest() {
        final Employee employee = Employee.builder()
                .username("alex")
                .password("123")
                .uniqNumber(999)
                .firstName("Alex")
                .lastName("Bob")
                .middleName("Jack")
                .active(true)
                .build();

        final long countBeforeSave = repo.count();
        final Employee saved = repo.saveAndFlush(employee);
        final long countAfterSave = repo.count();

        assertEquals(countAfterSave, countBeforeSave + 1);
        assertNotNull(saved);
        assertNotNull(saved.getId());
    }
}