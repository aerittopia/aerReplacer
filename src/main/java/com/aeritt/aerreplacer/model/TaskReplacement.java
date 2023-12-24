package com.aeritt.aerreplacer.model;

import com.aeritt.aerreplacer.model.replace.ReplaceSection;
import com.aeritt.aerreplacer.model.search.SearchSection;

import java.util.ArrayList;
import java.util.List;

public class TaskReplacement {
    public String id;
    public boolean enabled;
    public SearchSection search = new SearchSection();
    public ReplaceSection replace = new ReplaceSection();
    public List<String> allowedTasks = new ArrayList<>();
}