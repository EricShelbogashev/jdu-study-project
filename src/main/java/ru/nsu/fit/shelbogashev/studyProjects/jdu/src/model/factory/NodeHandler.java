package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

import java.nio.file.Path;
import java.util.Collection;

public interface NodeHandler {
    NodeView createNode(@NotNull Path path,
                        @Nullable Collection<NodeView> children,
                        @Nullable NodeFactoryConfiguration context,
                        @NotNull ExceptionTracer exceptionTracer);

    default int order() {
        return Integer.MAX_VALUE;
    }
}
