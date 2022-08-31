package ru.diasoft.spring.greetingproducer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    @GetMapping("/greeting/{username}")
    public String greeting(@PathVariable("username") String username) {
        return "Hello, " + username;
    }
}
