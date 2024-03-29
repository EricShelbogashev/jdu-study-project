package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.TreeBuilderResult;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.exception.NodeFactoryException;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;

public class NodeFactory {
    public final Collection<NodeHandler> handlers;
    private final NodeFactoryConfiguration configuration;

    /**
     * Factory for creating {@link TreeBuilderResult#root()} nodes.
     * Uses a comparator that sorts handlers in ascending priority, and for the same priority, the order is preserved.
     *
     * @param configuration supplies node handlers and jdu options for node creation specification.
     */
    public NodeFactory(NodeFactoryConfiguration configuration) {
        this.handlers = configuration.handlers().stream().sorted(Comparator.comparing(NodeHandler::order)).toList();
        this.configuration = configuration;
    }

    public NodeFactoryConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Returns a node if there is a handler capable of creating it.
     *
     * @param path            the path to which the node will be bound.
     * @param children        child nodes of the node. Must be initialized or null.
     * @param exceptionTracer storage for errors that may occur during the creation process.
     * @return node describing the given path.
     * @throws NodeFactoryException if a handler for creating a node was not found.
     */
    public @NotNull NodeView get(Path path, Collection<NodeView> children, ExceptionTracer exceptionTracer) throws NodeFactoryException {
        for (NodeHandler handler : this.handlers) {
            NodeView node = handler.createNode(path, children, configuration, exceptionTracer);
            if (node != null) return node;
        }
        throw new NodeFactoryException("undefined path reference");
    }
}