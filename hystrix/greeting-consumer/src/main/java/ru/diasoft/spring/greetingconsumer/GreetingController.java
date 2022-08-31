package ru.diasoft.spring.greetingconsumer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GreetingController {
    private final GreetingService greetingService;

    @GetMapping("/get-greeting/{username}")
    public String getGreeting(@PathVariable("username") String username) {
        return greetingService.getGreeting(username);
    }
}
