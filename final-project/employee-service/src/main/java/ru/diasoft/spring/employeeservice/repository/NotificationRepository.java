package ru.diasoft.spring.employeeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.employeeservice.domain.Notification;

@Repository
@RestResource(path = "notifications")
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
