package ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.filter.api;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.api.Node;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.filter.model.FilterNode;

public abstract class AbstractFilter implements TreeFilter {

    public void apply(Node root) {
        apply(FilterNode.of(root));
    }

    @Override
    public abstract void apply(FilterNode root);
}
