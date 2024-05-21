package com.aeritt.aerreplacer.replacement;

import com.aeritt.aerreplacer.model.replace.ReplaceType;
import com.aeritt.aerreplacer.model.replacement.Replacement;
import com.aeritt.aerreplacer.model.search.SearchType;
import com.aeritt.aerreplacer.placeholder.PlaceholderProvider;
import com.aeritt.aerreplacer.replacement.content.ReplacementContent;
import com.google.inject.Singleton;
import eu.cloudnetservice.node.service.CloudService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Singleton
public abstract class AbstractReplacer<T extends Replacement> {
	protected final Random random = new Random();
	private final ReplacementSearcher replacementSearcher;
	private final PlaceholderProvider placeholderProvider;

	protected AbstractReplacer(ReplacementSearcher replacementSearcher, PlaceholderProvider placeholderProvider) {
		this.replacementSearcher = replacementSearcher;
		this.placeholderProvider = placeholderProvider;
	}

	public abstract void processReplacement(CloudService service);

	public List<String> getPlaceholders(T replacement) {
		return switch (replacement.getSearch().type) {
			case ALL, ALL_SPECIFIC -> replacement.getSearch().placeholders;
			case RANDOM, RANDOM_SPECIFIC -> Collections.singletonList(
					replacement.getSearch().placeholders.get(random.nextInt(replacement.getSearch().placeholders.size()))
			);
			case FIRST, FIRST_SPECIFIC -> Collections.singletonList(replacement.getSearch().placeholders.get(0));
		};
	}

	public List<T> filterReplacements(List<T> replacements, String name) {
		return replacements.stream()
				.filter(replacement -> {
					for (String task : replacement.getAllowed()) {
						if (task.startsWith("$") || task.contains("*")) {
							String pattern = task.replace("*", ".*");
							if (task.startsWith("$")) {
								pattern = pattern.substring(1);
							}
							if (name.matches(pattern)) {
								return true;
							}
						} else if (name.equals(task)) {
							return true;
						}
					}
					return false;
				})
				.collect(Collectors.toList());
	}

	public List<Path> search(T replacement, List<String> placeholders, Path servicePath) {
		List<Path> searchResult = new ArrayList<>();

		if (replacement.getSearch().type.equals(SearchType.ALL_SPECIFIC)
				|| replacement.getSearch().type.equals(SearchType.RANDOM_SPECIFIC)
				|| replacement.getSearch().type.equals(SearchType.FIRST_SPECIFIC)) {
			searchResult.addAll(replacementSearcher.search(placeholders, servicePath, replacement.getSearch().paths));
		} else {
			searchResult.addAll(replacementSearcher.search(placeholders, servicePath));
		}

		return searchResult;
	}

	public String getContent(CloudService cloudService, T replacement) {
		List<String> contentList = replacement.getReplace().content;
		ReplaceType type = replacement.getReplace().type;

		String content = switch (type) {
			case RANDOM -> contentList.get(random.nextInt(contentList.size()));
			case FIRST -> contentList.get(0);
			case CONDITIONAL, EXTERNAL ->
				// Implement later
					null;
		};

		return placeholderProvider.replacePlaceholders(cloudService, content);
	}

	public void replace(ReplacementContent replacementContent) {
		Path path = replacementContent.path();
		String content = replacementContent.content();
		String placeholder = replacementContent.placeholder();

		try {
			List<String> lines = Files.readAllLines(path);
			List<String> modified = lines.stream()
					.map(line -> line.replace(placeholder, content))
					.collect(Collectors.toList());
			Files.write(path, modified);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}