package ru.diasoft.spring.resttemplate.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.diasoft.spring.resttemplate.model.Clues;

import java.util.Arrays;
import java.util.List;

@Log4j2
@Service
public class QuizService {
    private static final String ROOT_URL = "https://jservice.io/api";
    private final RestTemplate rest = new RestTemplate();

    public List<Clues> getClues() {
        final String url = ROOT_URL + "/clues?value=100";
        log.info("Request {}", url);
        final List<Clues> response = Arrays.asList(rest.getForObject(url, Clues[].class));
        response.forEach(log::info);
        return response;
    }
}
