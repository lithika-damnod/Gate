package com.example.backend.shell.commands;

import com.example.backend.shell.service.ShellEventService;
import com.example.backend.shell.service.ShellVendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.*;

@RequiredArgsConstructor
@ShellComponent
public class AddCommands {

    private final ShellVendorService shellVendorService;
    private final ShellEventService shellEventService;

    @ShellMethod(key="add", value="Add information about (vendor, event)")
    public void add(
        @ShellOption(help="Type to insert (vendor, event)") String type
    ) {
        if(Objects.equals(type, "vendor")) shellVendorService.add();
        else if(Objects.equals(type, "event")) shellEventService.add();
        else System.out.println("\u001B[31mInvalid type. Valid options are: vendor, event.\u001B[0m");
    }

}
