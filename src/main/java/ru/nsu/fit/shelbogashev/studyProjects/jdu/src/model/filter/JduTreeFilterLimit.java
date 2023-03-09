package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.filter;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

import java.util.List;

/**
 * Sorts the child nodes of the node in descending order and leaves limit - the rest are deleted.
 */
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
        List<NodeView> newChildren = root.children().stream().sorted().toList();
        if (newChildren.size() > limit) newChildren = newChildren.subList(0, limit);
        root.children().clear();
        root.children().addAll(newChildren);
    }
}
