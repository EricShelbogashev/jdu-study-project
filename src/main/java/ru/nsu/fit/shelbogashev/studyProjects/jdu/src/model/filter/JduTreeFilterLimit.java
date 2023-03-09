package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.filter;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

import java.util.List;

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
        List<NodeView> newChildren = root.children().stream().sorted().toList();
        if (newChildren.size() > limit) newChildren = newChildren.subList(0, limit - 1);
        root.children().clear();
        root.children().addAll(newChildren);
    }
}
