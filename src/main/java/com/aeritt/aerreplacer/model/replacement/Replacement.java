package com.aeritt.aerreplacer.model.replacement;

import com.aeritt.aerreplacer.model.replace.ReplaceSection;
import com.aeritt.aerreplacer.model.search.SearchSection;

import java.util.List;

public interface Replacement {
    String getId();

    boolean isEnabled();

    SearchSection getSearch();

    ReplaceSection getReplace();

    List<String> getAllowed();
}
