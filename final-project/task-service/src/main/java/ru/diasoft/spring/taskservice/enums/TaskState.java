package ru.diasoft.spring.taskservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum TaskState {
    NEW("Зарегистрировано"),
    DEVELOP("В работе"),
    DONE("Завершено")
    ;
    private final String state;

    public static Optional<TaskState> findByState(String state) {
        if (state == null) {
            return Optional.empty();
        }
        return Arrays.stream(values())
                .filter(s -> s.state.equals(state))
                .findFirst();
    }
}
