package com.example.backend.shell.commands;

import com.example.backend.shell.service.ShellEventService;
import com.example.backend.shell.service.ShellVendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;


@RequiredArgsConstructor
@ShellComponent
public class GetCommands {

    private final ShellVendorService shellVendorService;
    private final ShellEventService shellEventService;

    @ShellMethod(key="get", value="Retrieve information about vendor, event, ticket or user.")
    public void get(
            @ShellOption(help="Type to retrieve (vendor, event, ticket, user)") String type,
            @ShellOption(help="Optional <Id>, If not specified outputs all", defaultValue=ShellOption.NULL) String id
    ) {
        switch (type.toLowerCase()) {
            case "vendor" -> shellVendorService.get(id == null ? null : Integer.parseInt(id));
            case "event" -> shellEventService.get(id == null ? null : Integer.parseInt(id));
            default -> System.out.println("\u001B[31mInvalid type. Valid options are: vendor, event.\u001B[0m");
        };
    }
}