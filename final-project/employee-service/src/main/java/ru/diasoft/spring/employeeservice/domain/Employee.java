package ru.diasoft.spring.employeeservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Сущность "Сотрудник"
 */
@Entity
@Table(name = "t_employees")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {
    /**
     * Суррогатный первичный ключ
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Логин сотрудника
     */
    @Column(name = "username")
    private String username;

    /**
     * Пароль сотрудника
     */
    @Column(name = "password")
    private String password;

    /**
     * Уникальный номер сотрудника
     */
    @Column(name = "uniq_number")
    private Integer uniqNumber;

    /**
     * Имя сотрудника
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Фамилия сотрудника
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Отчество сотрудника
     */
    @Column(name = "middle_name")
    private String middleName;

    /**
     * Флаг: сотрудник активен
     */
    @Column(name = "is_active")
    private boolean active;

    /**
     * Команда сотрудника
     */
    @ManyToOne
    @JoinTable(name = "t_team_employee_link",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    public Team team;
}
