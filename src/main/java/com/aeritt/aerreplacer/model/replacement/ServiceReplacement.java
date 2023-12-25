package com.aeritt.aerreplacer.model.replacement;

import com.aeritt.aerreplacer.model.replace.ReplaceSection;
import com.aeritt.aerreplacer.model.search.SearchSection;

import java.util.ArrayList;
import java.util.List;

public class ServiceReplacement implements Replacement {
    public String id;
    public boolean enabled;
    public SearchSection search = new SearchSection();
    public ReplaceSection replace = new ReplaceSection();
    public List<String> allowedServices = new ArrayList<>();

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public SearchSection getSearch() {
        return search;
    }

    @Override
    public ReplaceSection getReplace() {
        return replace;
    }

    @Override
    public List<String> getAllowed() {
        return allowedServices;
    }
}