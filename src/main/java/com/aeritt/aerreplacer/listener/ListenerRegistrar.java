package com.aeritt.aerreplacer.listener;

import com.aeritt.aerreplacer.listener.service.ServicePostPrepareListener;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import eu.cloudnetservice.driver.event.EventManager;

import java.util.List;

@Singleton
public class ListenerRegistrar {
    private final Injector injector;
    private final EventManager eventManager;

    @Inject
    public ListenerRegistrar(Injector injector, EventManager eventManager) {
        this.injector = injector;
        this.eventManager = eventManager;
    }

    public void registerListeners() {
        List<Object> listeners = List.of(
                injector.getInstance(ServicePostPrepareListener.class)
        );

        for (Object listener : listeners) {
            eventManager.registerListener(listener);
        }
    }
}
