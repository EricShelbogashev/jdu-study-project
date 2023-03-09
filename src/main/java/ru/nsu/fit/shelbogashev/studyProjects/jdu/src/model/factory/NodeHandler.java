package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.Node;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

import java.nio.file.Path;
import java.util.Collection;

public interface NodeHandler {
    Node createNode(Path path, Collection<NodeView> children, NodeFactoryContext context, ExceptionTracer exceptionTracer);

    default int order() {
        return Integer.MAX_VALUE;
    }
}
