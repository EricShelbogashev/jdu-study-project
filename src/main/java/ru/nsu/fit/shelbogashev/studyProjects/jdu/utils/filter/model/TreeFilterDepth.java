package ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.filter.model;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.filter.api.AbstractFilter;

public class TreeFilterDepth extends AbstractFilter {
    private final long depth;

    public TreeFilterDepth(long depth) {
        this.depth = depth;
    }

    @Override
    public void apply(FilterNode root) {
        clearTailsOfRecursion(root);
    }

    private void clearTailsOfRecursion(FilterNode absoluteRoot) {
        clearTailsOfRecursion(absoluteRoot, 0);
    }

    private void clearTailsOfRecursion(FilterNode relativeRoot, long currentDepth) {
        if (currentDepth == this.depth) {
            relativeRoot.setChildren(null);
        }
        if (relativeRoot.childrenRaw() == null) return;
        for (FilterNode node : relativeRoot.childrenRaw()) {
            clearTailsOfRecursion(node, currentDepth + 1);
        }
    }
}
