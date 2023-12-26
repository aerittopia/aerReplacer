package com.aeritt.aerreplacer.placeholder;

import com.google.inject.Singleton;
import eu.cloudnetservice.node.service.CloudService;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Singleton
public class PlaceholderProvider {
    private final Map<String, Function<CloudService, String>> placeholders = new HashMap<>();

    public PlaceholderProvider() {
        placeholders.put("{nodeId}", service -> service.serviceId().nodeUniqueId());
        placeholders.put("{serviceName}", service -> service.serviceId().name());
        placeholders.put("{taskName}", service -> service.serviceId().taskName());
        placeholders.put("{serviceHost}", service -> service.serviceInfo().address().host());
        placeholders.put("{servicePort}", service -> String.valueOf(service.serviceInfo().address().port()));
    }

    public String replacePlaceholders(CloudService cloudService, String content) {
        for (Map.Entry<String, Function<CloudService, String>> entry : placeholders.entrySet()) {
            String placeholder = entry.getKey();
            if (content.contains(placeholder)) {
                String replacement = entry.getValue().apply(cloudService);
                content = content.replace(placeholder, replacement);
            }
        }
        return content;
    }
}
