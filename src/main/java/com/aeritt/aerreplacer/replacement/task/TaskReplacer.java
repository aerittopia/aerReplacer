package com.aeritt.aerreplacer.replacement.task;

import com.aeritt.aerreplacer.model.TaskReplacement;
import com.aeritt.aerreplacer.model.search.SearchType;
import com.aeritt.aerreplacer.replacement.ReplacementProvider;
import com.aeritt.aerreplacer.replacement.ReplacementSearcher;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.cloudnetservice.node.service.CloudService;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Singleton
public class TaskReplacer {
    private final ReplacementProvider replacementProvider;
    private final ReplacementSearcher replacementSearcher;
    private final Random random = new Random();

    @Inject
    public TaskReplacer(ReplacementProvider replacementProvider, ReplacementSearcher replacementSearcher) {
        this.replacementProvider = replacementProvider;
        this.replacementSearcher = replacementSearcher;
    }

    public void replace(CloudService service) {
        List<TaskReplacement> replacements = replacementProvider.getTaskReplacements();
        List<Path> searchResult = new ArrayList<>();

        String taskName = service.serviceId().taskName();
        Path servicePath = service.directory();

        replacements = filterReplacements(replacements, taskName);

        for (TaskReplacement replacement : replacements) {
            List<String> placeholders = getPlaceholders(replacement);

            if (replacement.search.type.equals(SearchType.ALL_SPECIFIC)
                    || replacement.search.type.equals(SearchType.RANDOM_SPECIFIC)
                    || replacement.search.type.equals(SearchType.FIRST_SPECIFIC)) {
                searchResult.addAll(replacementSearcher.search(placeholders, servicePath, replacement.search.paths));
                System.out.println("Found " + searchResult.size() + " files for task " + taskName + " with placeholders " + placeholders);
            } else {
                searchResult.addAll(replacementSearcher.search(placeholders, servicePath));
                System.out.println("Found " + searchResult.size() + " files for task " + taskName + " with placeholders " + placeholders);
            }
        }
    }

    private List<TaskReplacement> filterReplacements(List<TaskReplacement> replacements, String taskName) {
        return replacements.stream()
                .filter(replacement -> {
                    for (String task : replacement.allowedTasks) {
                        if (task.startsWith("$") || task.contains("*")) {
                            String pattern = task.replace("*", ".*");
                            if (task.startsWith("$")) {
                                pattern = pattern.substring(1);
                            }
                            if (taskName.matches(pattern)) {
                                return true;
                            }
                        } else if (taskName.equals(task)) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    private List<String> getPlaceholders(TaskReplacement replacement) {
        return switch (replacement.search.type) {
            case ALL, ALL_SPECIFIC -> replacement.search.placeholders;
            case RANDOM, RANDOM_SPECIFIC -> Collections.singletonList(
                    replacement.search.placeholders.get(random.nextInt(replacement.search.placeholders.size()))
            );
            case FIRST, FIRST_SPECIFIC -> Collections.singletonList(replacement.search.placeholders.get(0));
        };
    }
}
