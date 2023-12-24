package com.aeritt.aerreplacer.replacement;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Singleton
public class ReplacementManager {
    public final ReplacementProvider replacementProvider;

    @Inject
    public ReplacementManager(ReplacementProvider replacementProvider, @Named("replacementsFolder") Path replacementsFolder) {
        this.replacementProvider = replacementProvider;

        if (!Files.exists(replacementsFolder)) {
            try {
                Files.createDirectories(replacementsFolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void reloadReplacements() {
        replacementProvider.clearReplacements();
        replacementProvider.loadReplacements();
    }
}
