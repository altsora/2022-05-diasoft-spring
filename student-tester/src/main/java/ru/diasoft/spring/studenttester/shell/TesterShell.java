package ru.diasoft.spring.studenttester.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.spring.studenttester.service.MessageService;
import ru.diasoft.spring.studenttester.service.QuestionService;

import java.util.Scanner;

@ShellComponent
@RequiredArgsConstructor
public class TesterShell {
    private final QuestionService questionService;
    private final MessageService messageService;

    private String userName;

    @ShellMethod(value = "log in", key = {"login"})
    @ShellMethodAvailability(value = "loginCheck")
    public String login(@ShellOption(value = "-u") String userName) {
        this.userName = userName;
        return "Hello, " + userName;
    }

    @ShellMethod(value = "log out", key = {"logout"})
    @ShellMethodAvailability(value = "logoutCheck")
    public void logout() {
        System.out.println("Goodbye, " + userName);
        this.userName = null;
    }

    @ShellMethod("ping")
    public String hi() {
        return "hi";
    }

    @ShellMethod(value = "start test", key = "start")
    public void testing() {
        System.out.println(messageService.getMessage("test.start", userName));
        questionService.startTesting(new Scanner(System.in));
    }

    @ShellMethod(value = "print all questions", key = {"print-all"})
    public void printAll() {
        questionService.printAll();
    }

    public Availability loginCheck() {
        return userName == null ?
                Availability.available() :
                Availability.unavailable("you are already logged in");
    }

    public Availability logoutCheck() {
        return userName != null ?
                Availability.available() :
                Availability.unavailable("you are not logged in");
    }

    @ShellMethodAvailability({"print-all", "start"})
    public Availability auth() {
        return userName != null ?
                Availability.available() :
                Availability.unavailable("access denied");
    }
}
