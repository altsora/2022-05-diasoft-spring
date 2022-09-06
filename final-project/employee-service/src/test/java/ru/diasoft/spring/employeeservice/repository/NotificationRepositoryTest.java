package ru.diasoft.spring.employeeservice.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.diasoft.spring.employeeservice.domain.Notification;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Тесты репозитория уведомлений NotificationRepository")
class NotificationRepositoryTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private NotificationRepository repo;

    @Test
    @DisplayName("Сохранение сущности")
    void saveTest() {
        final Notification domain = Notification.builder()
                .employeeId(1)
                .uniqNumber(999)
                .message("text")
                .dateTime(LocalDateTime.MIN)
                .build();

        final long countBeforeSave = repo.count();
        final Notification saved = repo.saveAndFlush(domain);
        final long countAfterSave = repo.count();

        assertEquals(countAfterSave, countBeforeSave + 1);
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertNotNull(saved.getDateTime());
    }
}