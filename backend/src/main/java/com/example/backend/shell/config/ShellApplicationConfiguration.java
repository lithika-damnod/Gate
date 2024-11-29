package com.example.backend.shell.config;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

@Configuration
public class ShellApplicationConfiguration implements PromptProvider {

    @Override
    public AttributedString getPrompt() {
        return new AttributedString("gate> ", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }
}
