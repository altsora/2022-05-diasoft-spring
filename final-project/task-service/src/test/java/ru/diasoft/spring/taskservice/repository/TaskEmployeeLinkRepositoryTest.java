package ru.diasoft.spring.taskservice.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.diasoft.spring.taskservice.domain.TaskEmployeeLink;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Тесты репозитория задач TaskEmployeeLinkRepository")
class TaskEmployeeLinkRepositoryTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private TaskEmployeeLinkRepository repo;

    @Test
    @DisplayName("Сохранение сущности")
    void saveTest() {
        final TaskEmployeeLink domain = TaskEmployeeLink.builder()
                .taskId(1)
                .employeeId(2)
                .build();

        final long countBeforeSave = repo.count();
        final TaskEmployeeLink saved = repo.saveAndFlush(domain);
        final long countAfterSave = repo.count();

        assertEquals(countAfterSave, countBeforeSave + 1);
        assertNotNull(saved);
        assertNotNull(saved.getId());
    }
}