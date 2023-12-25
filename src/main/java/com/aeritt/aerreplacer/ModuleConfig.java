package com.aeritt.aerreplacer;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import eu.cloudnetservice.driver.event.EventManager;
import eu.cloudnetservice.node.command.CommandProvider;

import java.nio.file.Path;
import java.nio.file.Paths;

@Singleton
public class ModuleConfig extends AbstractModule {
    private final CommandProvider commandProvider;
    private final EventManager eventManager;

    public ModuleConfig(CommandProvider commandProvider, EventManager eventManager) {
        this.commandProvider = commandProvider;
        this.eventManager = eventManager;
    }

    @Override
    protected void configure() {
        bind(CommandProvider.class).toInstance(commandProvider);
        bind(EventManager.class).toInstance(eventManager);

        bind(Path.class).annotatedWith(Names.named("dataFolder")).toInstance(Paths.get("modules/aerReplacer"));
        bind(Path.class).annotatedWith(Names.named("replacementsFolder")).toInstance(Paths.get("modules/aerReplacer/replacements"));
    }
}
