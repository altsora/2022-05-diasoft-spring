package ru.diasoft.spring.employeeservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

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

//    @Fetch(FetchMode.SUBSELECT)
//    @BatchSize(size = 15)
//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "t_team_employee_link",
//            joinColumns = @JoinColumn(name = "team_id"),
//            inverseJoinColumns = @JoinColumn(name = "employee_id"))
//    private List<Employee> employees;
}
