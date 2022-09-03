package ru.diasoft.spring.integration.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.diasoft.spring.integration.domain.Programmer;

import java.util.Collection;

@MessagingGateway
public interface School {

    @Gateway(requestChannel = "programmersChanel", replyChannel = "finishStudyChanel")
    void massStudy(Collection<Programmer> programmers);
}
