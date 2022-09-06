package ru.diasoft.spring.taskservice.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.diasoft.spring.taskservice.domain.Task;
import ru.diasoft.spring.taskservice.enums.TaskState;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Тесты репозитория задач TaskRepository")
class TaskRepositoryTest {
    private static final Integer EMPLOYEE_ID = 1;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private TaskRepository repo;

    @Test
    @DisplayName("Сохранение сущности")
    void saveTest() {
        final Task domain = Task.builder()
                .uniqNumber(123)
                .title("Заголовок")
                .state(TaskState.NEW)
                .build();

        final long countBeforeSave = repo.count();
        final Task saved = repo.saveAndFlush(domain);
        final long countAfterSave = repo.count();

        assertEquals(countAfterSave, countBeforeSave + 1);
        assertNotNull(saved);
        assertNotNull(saved.getId());
    }

    @Test
    void getAllTasksByEmployeeTest() {
        final List<Task> actual = repo.findAllTasksByEmployeeId(EMPLOYEE_ID);
        assertNotNull(actual);
        assertEquals(2, actual.size());
        actual.forEach(task -> {
            assertNotNull(task);
            assertNotNull(task.getId());
        });
    }
}