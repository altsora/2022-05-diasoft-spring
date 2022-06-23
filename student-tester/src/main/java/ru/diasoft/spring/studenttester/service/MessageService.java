package ru.diasoft.spring.studenttester.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Log4j2
@Service
public class MessageService {
    private final MessageSource messageSource;
    private final Locale locale;

    public MessageService(MessageSource messageSource, @Value("${testing.locale}") String localeTag) {
        this.messageSource = messageSource;
        this.locale = Locale.forLanguageTag(localeTag);
        log.info("locale used: [{}]", locale);
    }

    public String getMessage(final String key, Object... args) {
        return messageSource.getMessage(key, args, locale);
    }

    public String getMessage(final String key) {
        return messageSource.getMessage(key, null, locale);
    }
}
