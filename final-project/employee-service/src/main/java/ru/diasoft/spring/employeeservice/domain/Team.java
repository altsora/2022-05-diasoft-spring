package ru.diasoft.spring.employeeservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "t_teams")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    /**
     * Суррогатный первичный ключ
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Уникальный номер команды
     */
    @Column(name = "uniq_number")
    private Integer uniqNumber;

    /**
     * Название команды
     */
    @Column(name = "name")
    private String name;

    /**
     * Флаг: команда активна
     */
    @Column(name = "is_active")
    private boolean active;
}
