package ru.diasoft.spring.monitoring.actuator;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.diasoft.spring.monitoring.repository.BookRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookCountHealthIndicator implements HealthIndicator {
    private final BookRepository bookRepository;

    @Override
    public Health health() {
        log.info("Вызов BookCountHealthIndicator.health");
        final long bookCount = bookRepository.count();
        final Status status = bookCount == 0 ? Status.DOWN : Status.UP;
        return Health.status(status).withDetail("count", bookCount).build();
    }
}
