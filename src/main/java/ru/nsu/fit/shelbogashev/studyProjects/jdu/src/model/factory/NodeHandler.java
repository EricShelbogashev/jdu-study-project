package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.Node;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

import java.nio.file.Path;
import java.util.Collection;

public interface NodeHandler {
    Node createNode(@NotNull Path path,
                    @Nullable Collection<NodeView> children,
                    @Nullable NodeFactoryContext context,
                    @NotNull ExceptionTracer exceptionTracer);

    default int order() {
        return Integer.MAX_VALUE;
    }
}
