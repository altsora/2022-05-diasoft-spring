package ru.diasoft.spring.taskservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Доменная сущность "Связь задачи и сотрудника"
 */
@Entity
@Table(name = "t_task_employee_link")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskEmployeeLink {
    /**
     * Суррогатный первичный ключ
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * ID задачи
     */
    @Column(name = "task_id")
    private Integer taskId;

    /**
     * ID сотрудника
     */
    @Column(name = "employee_id")
    private Integer employeeId;
}
