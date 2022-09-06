package ru.diasoft.spring.employeeservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    /**
     * Суррогатный первичный ключ
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "uniq_number")
    private Integer uniqNumber;

    /**
     * ID сотрудника
     */
    @Column(name = "employee_id")
    private Integer employeeId;

    /**
     * Дата и время создания уведомления
     */
    @Column(name = "datetime")
    private LocalDateTime dateTime;

    /**
     * Текст уведомления
     */
    @Column(name = "message")
    private String message;
}
