package ru.diasoft.spring.employeeservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_teams")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team implements Serializable {
    /**
     * Суррогатный первичный ключ
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_id_seq")
    @SequenceGenerator(name = "team_id_seq", sequenceName = "team_id_seq", allocationSize = 1)
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

    /**
     * Сотрудники команды
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_team_employee_link",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        employees.removeIf(next -> next.getId().equals(employee.getId()));
    }
}
