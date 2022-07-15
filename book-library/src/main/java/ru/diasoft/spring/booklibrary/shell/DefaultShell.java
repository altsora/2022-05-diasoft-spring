package ru.diasoft.spring.booklibrary.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class DefaultShell {
    @ShellMethod(value = "ping", key = {"ping", "p"})
    public String ping() {
        return "pong";
    }
}
