package ru.diasoft.spring.kafka.producer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {
    @GetMapping("/")
    public String ping() {
        return "I am producer service!";
    }
}
