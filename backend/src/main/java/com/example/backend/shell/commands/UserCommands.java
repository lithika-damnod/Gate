package com.example.backend.shell.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class UserCommands {
    @ShellMethod("greeting")
    public String hello(
        @ShellOption(value = {"-n", "--name"}, defaultValue = "Lithika") String name
    ) {
        return "hello, " + name;
    }
}
