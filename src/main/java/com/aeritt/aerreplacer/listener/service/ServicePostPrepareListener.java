package com.aeritt.aerreplacer.listener.service;

import com.aeritt.aerreplacer.replacement.service.ServiceReplacer;
import com.aeritt.aerreplacer.replacement.task.TaskReplacer;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.cloudnetservice.driver.event.EventListener;
import eu.cloudnetservice.node.event.service.CloudServicePostPrepareEvent;

@Singleton
public class ServicePostPrepareListener {
    private final ServiceReplacer serviceReplacer;
    private final TaskReplacer taskReplacer;

    @Inject
    public ServicePostPrepareListener(ServiceReplacer serviceReplacer, TaskReplacer taskReplacer) {
        this.serviceReplacer = serviceReplacer;
        this.taskReplacer = taskReplacer;
    }

    @EventListener
    public void onServicePostPrepare(CloudServicePostPrepareEvent event) {
        serviceReplacer.processReplacement(event.service());
        taskReplacer.processReplacement(event.service());
    }
}
