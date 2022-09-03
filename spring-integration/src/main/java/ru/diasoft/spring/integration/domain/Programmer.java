package ru.diasoft.spring.integration.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Programmer {
    private static int count = 0;

    private int id;
    private String name;
    private Grade grade;
    private boolean hasReward;

    public Programmer(String name) {
        this.id = ++count;
        this.name = name;
        this.grade = Grade.NO_GRADE;
    }

    public boolean hasReward() {
        return hasReward;
    }

    public boolean hasNotReward() {
        return !hasReward;
    }

    public Programmer reward() {
        this.hasReward = true;
        return this;
    }

    public Programmer noReward() {
        this.hasReward = false;
        return this;
    }

    public boolean hasLastGrade() {
        return grade == Grade.SENIOR;
    }
}
