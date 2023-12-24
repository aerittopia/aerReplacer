package com.aeritt.aerreplacer.command.management;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.cloudnetservice.node.command.CommandProvider;

@Singleton
public class CommandRegistrar {
    private final CommandProvider commandProvider;

    @Inject
    public CommandRegistrar(CommandProvider commandProvider) {
        this.commandProvider = commandProvider;
    }

    public void registerCommand(Object commandClass) {
        commandProvider.register(commandClass);
    }
}
