package ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.filter.api;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.filter.model.FilterNode;

public interface TreeFilter {
    void apply(FilterNode root);
}
