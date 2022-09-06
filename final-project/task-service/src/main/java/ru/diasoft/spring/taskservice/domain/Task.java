package ru.diasoft.spring.taskservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.diasoft.spring.taskservice.enums.TaskState;
import ru.diasoft.spring.taskservice.utils.converters.TaskStateConverter;

import javax.persistence.*;

/**
 * Доменная сущность "Задача"
 */
@Entity
@Table(name = "t_tasks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    /**
     * Суррогатный первичный ключ
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Название задачи
     */
    @Column(name = "title")
    private String title;

    /**
     * Состояние задачи
     */
    @Column(name = "state")
    @Convert(converter = TaskStateConverter.class)
    private TaskState state;
}
