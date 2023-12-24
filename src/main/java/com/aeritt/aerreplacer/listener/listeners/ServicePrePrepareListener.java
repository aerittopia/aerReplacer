package com.aeritt.aerreplacer.listener.listeners;

import com.aeritt.aerreplacer.replacement.service.ServiceReplacer;
import com.aeritt.aerreplacer.replacement.task.TaskReplacer;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.cloudnetservice.driver.event.EventListener;
import eu.cloudnetservice.node.event.service.CloudServicePrePrepareEvent;

@Singleton
public class ServicePrePrepareListener {
    private final ServiceReplacer serviceReplacer;
    private final TaskReplacer taskReplacer;

    @Inject
    public ServicePrePrepareListener(ServiceReplacer serviceReplacer, TaskReplacer taskReplacer) {
        this.serviceReplacer = serviceReplacer;
        this.taskReplacer = taskReplacer;
    }

    @EventListener
    public void onServicePrePrepare(CloudServicePrePrepareEvent event) {
        serviceReplacer.replace(event.service());
        taskReplacer.replace(event.service());
    }
}
