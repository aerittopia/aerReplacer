package com.aeritt.aerreplacer.replacement;

import com.aeritt.aerreplacer.config.ReplacementConfig;
import com.aeritt.aerreplacer.model.replacement.Replacement;
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
    private final List<Replacement> replacements = new ArrayList<>();

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

            replacements.addAll(replacement.services);
            replacements.addAll(replacement.tasks);
        }
    }

    public List<? extends Replacement> getReplacements() {
        return replacements;
    }

    public void clearReplacements() {
        replacements.clear();
    }
}
