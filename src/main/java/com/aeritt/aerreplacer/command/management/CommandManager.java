package com.aeritt.aerreplacer.command.management;

import com.aeritt.aerreplacer.command.commands.ReloadCommand;
import com.aeritt.aerreplacer.config.command.CommandConfig;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton
public class CommandManager {
    private final Injector injector;
    private final CommandRegistrar commandRegistrar;
    private final CommandConfig commandConfig;

    @Inject
    public CommandManager(Injector injector, CommandRegistrar commandRegistrar, CommandConfig commandConfig) {
        this.injector = injector;
        this.commandRegistrar = commandRegistrar;
        this.commandConfig = commandConfig;
    }

    public void registerCommands() {
        if (commandConfig.reloadCommand.enabled) {
            Object reloadCommandClass = injector.getInstance(ReloadCommand.class);
            commandRegistrar.registerCommand(reloadCommandClass);
        }
    }
}
