package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.filter;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

public class JduTreeFilterDepth implements JduTreeFilter {
    private final int depth;

    public JduTreeFilterDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public NodeView apply(NodeView root) {
        cutTreeByLevelRecursively(root);
        return root;
    }

    protected void cutTreeByLevelRecursively(NodeView root) {
        cutTreeByLevelRecursively(root, 0);
    }

    private void cutTreeByLevelRecursively(NodeView root, int currentLevel) {
        if (root.children() == null) return;
        if (currentLevel == depth) {
            root.children().clear();
            return;
        }
        for (NodeView child : root.children()) {
            cutTreeByLevelRecursively(child, currentLevel + 1);
        }
    }
}
