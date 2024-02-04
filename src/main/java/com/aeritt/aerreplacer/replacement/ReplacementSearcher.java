package com.aeritt.aerreplacer.replacement;

import com.google.inject.Singleton;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Singleton
public class ReplacementSearcher {
	public List<Path> search(List<String> placeholders, Path path, List<String> customPaths) {
		List<Path> result = new ArrayList<>();

		for (String customPath : customPaths) {
			result.addAll(search(placeholders, path.resolve(customPath)));
		}

		return result;
	}

	public List<Path> search(List<String> placeholders, Path path) {
		List<Path> result = new ArrayList<>();

		if (!Files.exists(path)) {
			return result;
		}

		try (Stream<Path> paths = Files.walk(path)) {
			paths.filter(Files::isRegularFile)
					.forEach(file -> {
						try {
							String content = new String(Files.readAllBytes(file));
							for (String placeholder : placeholders) {
								if (content.contains(placeholder)) {
									result.add(file);
									break;
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
}
