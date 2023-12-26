package com.aeritt.aerreplacer.replacement.content;

import java.nio.file.Path;

public record ReplacementContent(Path path, String content, String placeholder) {
}
