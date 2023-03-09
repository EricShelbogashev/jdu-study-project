package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.filter;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

public interface JduTreeFilter {
    NodeView apply(NodeView root);
}
