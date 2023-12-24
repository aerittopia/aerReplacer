package com.aeritt.aerreplacer.replacement;

import com.aeritt.aerreplacer.config.ReplacementConfig;
import com.aeritt.aerreplacer.model.ServiceReplacement;
import com.aeritt.aerreplacer.model.TaskReplacement;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ReplacementProvider {
    private final Injector injector;

    private final Path replacementsFolder;
    private List<TaskReplacement> taskReplacements = new ArrayList<>();
    private List<ServiceReplacement> serviceReplacements = new ArrayList<>();

    @Inject
    public ReplacementProvider(Injector injector, @Named("replacementsFolder") Path replacementsFolder) {
        this.injector = injector;
        this.replacementsFolder = replacementsFolder;
    }

    public void loadReplacements() {
        List<Path> files = new ArrayList<>();
        try {
            files = Files.list(replacementsFolder)
                    .filter(path -> path.toString().endsWith(".yml"))
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Path file : files) {
            ReplacementConfig replacement = injector.getInstance(ReplacementConfig.class);
            replacement.reload(file);

            serviceReplacements.addAll(replacement.services);
            taskReplacements.addAll(replacement.tasks);
        }
    }

    public List<TaskReplacement> getTaskReplacements() {
        return taskReplacements;
    }

    public void clearReplacements() {
        taskReplacements.clear();
        serviceReplacements.clear();
    }

    public void setServiceReplacements(List<ServiceReplacement> serviceReplacements) {
        this.serviceReplacements = serviceReplacements;
    }
}
