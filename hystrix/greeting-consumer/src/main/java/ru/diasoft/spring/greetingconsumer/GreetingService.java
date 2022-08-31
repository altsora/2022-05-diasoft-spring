package ru.diasoft.spring.greetingconsumer;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GreetingService {
    @Value("${greeting-producer.url}")
    private String serviceUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    @HystrixCommand(fallbackMethod = "defaultGreeting")
    public String getGreeting(String username) {
        return restTemplate.getForObject(serviceUrl + "/greeting/{username}", String.class, username);
    }

    private String defaultGreeting(String username) {
        return "Hello, anon";
    }

}
