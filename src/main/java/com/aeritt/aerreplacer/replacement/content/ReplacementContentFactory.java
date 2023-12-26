package com.aeritt.aerreplacer.replacement.content;

import com.google.inject.Singleton;

import java.nio.file.Path;

@Singleton
public class ReplacementContentFactory {
    public ReplacementContent create(Path path, String content, String placeholder) {
        return new ReplacementContent(path, content, placeholder);
    }
}
