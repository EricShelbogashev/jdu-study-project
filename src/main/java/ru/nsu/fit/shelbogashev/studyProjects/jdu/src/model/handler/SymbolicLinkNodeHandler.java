package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.handler;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.ExceptionTracer;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeFactoryContext;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeHandler;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.Node;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.SymbolicLinkBehavior;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.SymbolicLinkNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public class SymbolicLinkNodeHandler implements NodeHandler {
    static SymbolicLinkBehavior DEFAULT_BEHAVIOR = SymbolicLinkBehavior.LIKE_A_FILE;

    @Override
    public Node createNode(Path path, Collection<NodeView> children, NodeFactoryContext context, ExceptionTracer exceptionTracer) {
        if (!Files.isSymbolicLink(path)) return null;
        try {
            SymbolicLinkBehavior behavior = Boolean.TRUE.equals(context.jduOptions().getSymbolicLinkFollow())
                    ? SymbolicLinkBehavior.LIKE_A_DIRECTORY
                    : SymbolicLinkBehavior.LIKE_A_FILE;
            return new SymbolicLinkNode(path, children, behavior);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int order() {
        return 0;
    }
}
