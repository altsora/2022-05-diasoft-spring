package ru.diasoft.spring.integration.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import ru.diasoft.spring.integration.domain.Programmer;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class MonitoringService implements MessageHandler {
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        final List<Programmer> programmers = (List<Programmer>)message.getPayload();
        final List<Programmer> successfulProgrammers = programmers.stream()
                .filter(Programmer::hasReward)
                .collect(Collectors.toList());
        final List<Programmer> loserProgrammers = programmers.stream()
                .filter(Programmer::hasNotReward)
                .collect(Collectors.toList());

        final String successMsg = "Программистов дошло до конца: {}. Победители: {}";
        final String loserMsg = "Программистов не справились с обучением: {}. Выбывшие: {}";
        log.info("Учебный курс завершён! Подведение итогов группы:");
        printProgrammersInfo(successMsg, successfulProgrammers);
        printProgrammersInfo(loserMsg, loserProgrammers);
        log.info("===================================================================");
    }

    private void printProgrammersInfo(String msg, List<Programmer> programmers) {
        final String names = programmers.stream()
                .map(p -> p.getName() + " (id = " + p.getId() + ")")
                .collect(Collectors.joining(", "));
        log.info(msg, programmers.size(), names.isEmpty() ? "Нет таких" : names);
    }
}
