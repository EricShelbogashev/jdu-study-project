package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.exception.NodeFactoryException;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.Node;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;

public class NodeFactory {
    public final Collection<NodeHandler> units;
    private final NodeFactoryContext context;

    public NodeFactory(NodeFactoryConfiguration configuration, NodeFactoryContext context) {
        this.units = new TreeSet<>((handler1, handler2) -> {
            if (handler2.order() == handler1.order()) {
                if (handler1.equals(handler2)) return 0;
                return 1;
            }
            return handler1.order() - handler2.order();
        });
        this.context = context;
        this.units.addAll(configuration.handlers());
    }

    public Node get(Path path, Collection<NodeView> children, ExceptionTracer exceptionTracer) throws NodeFactoryException {
        for (NodeHandler handler : this.units) {
            Node node = handler.createNode(path, children, context, exceptionTracer);
            if (node != null) return node;
        }
        throw new NodeFactoryException("undefined path reference");
    }
}