package com.aeritt.aerreplacer.replacement.service;

import com.aeritt.aerreplacer.model.replacement.ServiceReplacement;
import com.aeritt.aerreplacer.placeholder.PlaceholderProvider;
import com.aeritt.aerreplacer.replacement.AbstractReplacer;
import com.aeritt.aerreplacer.replacement.ReplacementProvider;
import com.aeritt.aerreplacer.replacement.ReplacementSearcher;
import com.aeritt.aerreplacer.replacement.content.ReplacementContent;
import com.aeritt.aerreplacer.replacement.content.ReplacementContentFactory;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.cloudnetservice.node.service.CloudService;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class ServiceReplacer extends AbstractReplacer<ServiceReplacement> {
	private final ReplacementProvider replacementProvider;
	private final ReplacementContentFactory replacementContentFactory;
	private final List<ReplacementContent> replacementContents = new ArrayList<>();

	@Inject
	public ServiceReplacer(ReplacementSearcher replacementSearcher, PlaceholderProvider placeholderProvider,
	                       ReplacementProvider replacementProvider, ReplacementContentFactory replacementContentFactory) {
		super(replacementSearcher, placeholderProvider);

		this.replacementProvider = replacementProvider;
		this.replacementContentFactory = replacementContentFactory;
	}

	@Override
	public void processReplacement(CloudService cloudService) {
		List<ServiceReplacement> replacements = replacementProvider.getReplacements()
				.stream()
				.filter(replacement -> replacement instanceof ServiceReplacement)
				.map(replacement -> (ServiceReplacement) replacement)
				.collect(Collectors.toList());
		replacementContents.clear();

		String serviceName = cloudService.serviceId().name();
		Path servicePath = cloudService.directory();

		replacements = filterReplacements(replacements, serviceName);
		if (replacements.isEmpty()) {
			return;
		}

		for (ServiceReplacement replacement : replacements) {
			List<String> placeholders = getPlaceholders(replacement);
			List<Path> searchResults = search(replacement, placeholders, servicePath);

			for (Path path : searchResults) {
				String content = getContent(cloudService, replacement);
				for (String placeholder : placeholders) {
					ReplacementContent replacementContent = replacementContentFactory.create(path, content, placeholder);
					replacementContents.add(replacementContent);
				}
			}
		}

		for (ReplacementContent replacementContent : replacementContents) {
			replace(replacementContent);
		}
	}
}

