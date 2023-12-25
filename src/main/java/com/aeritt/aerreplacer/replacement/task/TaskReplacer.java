package com.aeritt.aerreplacer.replacement.task;

import com.aeritt.aerreplacer.model.replacement.TaskReplacement;
import com.aeritt.aerreplacer.replacement.AbstractReplacer;
import com.aeritt.aerreplacer.replacement.ReplacementProvider;
import com.aeritt.aerreplacer.replacement.ReplacementSearcher;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.cloudnetservice.node.service.CloudService;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class TaskReplacer extends AbstractReplacer<TaskReplacement> {
    private final ReplacementProvider replacementProvider;
    private final ReplacementSearcher replacementSearcher;
    private List<Path> searchResult = new ArrayList<>();

    @Inject
    public TaskReplacer(ReplacementProvider replacementProvider, ReplacementSearcher replacementSearcher) {
        this.replacementProvider = replacementProvider;
        this.replacementSearcher = replacementSearcher;
    }

    @Override
    public void processReplacement(CloudService service) {
        List<TaskReplacement> replacements = replacementProvider.getReplacements()
                .stream()
                .filter(replacement -> replacement instanceof TaskReplacement)
                .map(replacement -> (TaskReplacement) replacement)
                .collect(Collectors.toList());

        searchResult.clear();

        String taskName = service.serviceId().taskName();
        Path servicePath = service.directory();

        replacements = filterReplacements(replacements, taskName);

        for (TaskReplacement replacement : replacements) {
            searchResult = search(replacement, servicePath, replacementSearcher);
            if (!searchResult.isEmpty()) {
                break;
            }


        }
    }
}
