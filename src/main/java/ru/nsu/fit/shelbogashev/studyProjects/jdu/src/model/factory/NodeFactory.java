package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.NodeViewTree;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.exception.NodeFactoryException;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.Node;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

import java.nio.file.Path;
import java.util.Collection;
import java.util.TreeSet;

public class NodeFactory {
    public final Collection<NodeHandler> units;
    private final NodeFactoryContext context;

    /**
     * Factory for creating {@link NodeViewTree#root()} nodes.
     *
     * @param configuration supplies node handlers.
     * @param context       supplies jdu options for node creation specification.
     */
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

    /**
     * Returns a node if there is a handler capable of creating it.
     *
     * @param path            the path to which the node will be bound.
     * @param children        child nodes of the node. Must be initialized or null.
     * @param exceptionTracer storage for errors that may occur during the creation process.
     * @return node describing the given path.
     * @throws NodeFactoryException if a handler for creating a node was not found.
     */
    public @NotNull Node get(Path path, Collection<NodeView> children, ExceptionTracer exceptionTracer) throws NodeFactoryException {
        for (NodeHandler handler : this.units) {
            Node node = handler.createNode(path, children, context, exceptionTracer);
            if (node != null) return node;
        }
        throw new NodeFactoryException("undefined path reference");
    }
}