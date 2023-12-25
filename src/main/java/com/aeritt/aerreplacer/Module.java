package com.aeritt.aerreplacer;

import com.aeritt.aerreplacer.command.management.CommandManager;
import com.aeritt.aerreplacer.config.ConfigManager;
import com.aeritt.aerreplacer.listener.ListenerRegistrar;
import com.aeritt.aerreplacer.replacement.ReplacementProvider;
import com.google.inject.Guice;
import com.google.inject.Injector;
import eu.cloudnetservice.driver.event.EventManager;
import eu.cloudnetservice.driver.module.ModuleLifeCycle;
import eu.cloudnetservice.driver.module.ModuleTask;
import eu.cloudnetservice.driver.module.driver.DriverModule;
import eu.cloudnetservice.node.command.CommandProvider;

public final class Module extends DriverModule {
    private Injector injector;

    @ModuleTask(lifecycle = ModuleLifeCycle.LOADED)
    public void onLoad(CommandProvider commandProvider, EventManager eventManager) {
        injector = Guice.createInjector(new ModuleConfig(commandProvider, eventManager));

        injector.getInstance(ConfigManager.class).load();
        injector.getInstance(ConfigManager.class).save();
    }

    @ModuleTask(lifecycle = ModuleLifeCycle.STARTED)
    public void onStart() {
        injector.getInstance(ReplacementProvider.class).loadReplacements();
        injector.getInstance(CommandManager.class).registerCommands();
        injector.getInstance(ListenerRegistrar.class).registerListeners();
    }
}
