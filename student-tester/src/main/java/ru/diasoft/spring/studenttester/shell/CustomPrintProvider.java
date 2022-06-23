package ru.diasoft.spring.studenttester.shell;

import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class CustomPrintProvider implements PromptProvider {
    @Override
    public AttributedString getPrompt() {
        return new AttributedString("Open AI>");
    }
}
