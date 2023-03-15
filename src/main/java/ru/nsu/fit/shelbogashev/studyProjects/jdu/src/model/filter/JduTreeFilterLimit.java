package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.filter;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

import java.util.List;

/**
 * Sorts the child nodes of the node in descending order and leaves limit - the rest are deleted.
 */
@SuppressWarnings("ALL")
public class JduTreeFilterLimit implements JduTreeFilter {
    private final int limit;

    public JduTreeFilterLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public NodeView apply(NodeView root) {
        cutTreeByChildCount(root);
        return root;
    }

    protected void cutTreeByChildCount(NodeView root) {
        if (root.children() == null) return;
        for (NodeView child : root.children()) {
            cutTreeByChildCount(child);
        }
        List<NodeView> newChildren = root.children().stream().sorted().limit(limit).toList();
        root.children().clear();
        root.children().addAll(newChildren);
    }
}
