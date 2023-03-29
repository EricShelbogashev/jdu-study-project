package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.handler;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.ExceptionTracer;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeFactoryConfiguration;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeHandler;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.formatter.Size;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.RegularFileNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public class RegularFileNodeHandler implements NodeHandler {

    @Override
    public NodeView createNode(@NotNull Path path,
                               Collection<NodeView> children,
                               NodeFactoryConfiguration configuration,
                               @NotNull ExceptionTracer exceptionTracer) {
        if (!Files.isRegularFile(path)) return null;
        try {
            return new RegularFileNode(path, children);
        } catch (IOException | SecurityException e) {
            exceptionTracer.put(e);
            return new RegularFileNode(path, children, Size.SIZE_UNKNOWN);
        }
    }
}
