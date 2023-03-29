package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.handler;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.ExceptionTracer;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeFactoryConfiguration;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeHandler;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.DirectoryNode;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.Node;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public class DirectoryNodeHandler implements NodeHandler {
    @Override
    public Node createNode(@NotNull Path path,
                           Collection<NodeView> children,
                           NodeFactoryConfiguration configuration,
                           @NotNull ExceptionTracer exceptionTracer) {
        if (!Files.isDirectory(path)) return null;
        if (Files.isSymbolicLink(path)) return null;
        return new DirectoryNode(path, children);
    }

    @Override
    public int order() {
        return Integer.MAX_VALUE;
    }
}
