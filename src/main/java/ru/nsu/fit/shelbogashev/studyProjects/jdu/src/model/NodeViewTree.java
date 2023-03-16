package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.ExceptionTracer;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.ExceptionTracerImpl;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeFactory;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeFactoryConfiguration;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

import java.nio.file.Path;

public class NodeViewTree {
    private final NodeView root;
    private final ExceptionTracer tracer;

    /**
     * Creates a tree, each node of which describes a unit of the file system.
     */
    public NodeViewTree(@NotNull Path path, @NotNull NodeFactoryConfiguration configuration) {
        this.tracer = new ExceptionTracerImpl();
        NodeFactory factory = new NodeFactory(configuration);
        NodeViewTreeBuildRecursivelyAction actionBuild = new NodeViewTreeBuildRecursivelyActionImpl(
                configuration.depth(),
                configuration.symbolicLinkFollow()
        );
        root = actionBuild.apply(path, factory, tracer);
    }

    public @NotNull NodeView root() {
        return this.root;
    }

    public @NotNull ExceptionTracer exceptions() {
        return this.tracer;
    }
}
