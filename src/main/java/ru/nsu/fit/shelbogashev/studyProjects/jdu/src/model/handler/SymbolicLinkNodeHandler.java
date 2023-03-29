package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.handler;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.ExceptionTracer;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeFactoryConfiguration;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeHandler;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.Node;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.SymbolicLinkBehavior;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.SymbolicLinkNode;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;

public class SymbolicLinkNodeHandler implements NodeHandler {
    @Override
    public Node createNode(@NotNull Path path,
                           Collection<NodeView> children,
                           NodeFactoryConfiguration configuration,
                           @NotNull ExceptionTracer exceptionTracer) {
        try {
            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
            if (!attributes.isSymbolicLink()) return null;
        } catch (IOException e) {
            exceptionTracer.put(e);
            return null;
        }
        try {
            SymbolicLinkBehavior behavior = Boolean.TRUE.equals(configuration.options().symbolicLinkFollow())
                    ? SymbolicLinkBehavior.LIKE_A_DIRECTORY
                    : SymbolicLinkBehavior.LIKE_A_FILE;
            return new SymbolicLinkNode(path, children, behavior);
        } catch (IOException e) {
            exceptionTracer.put(e);
            return null;
        }
    }

    @Override
    public int order() {
        return 0;
    }
}
