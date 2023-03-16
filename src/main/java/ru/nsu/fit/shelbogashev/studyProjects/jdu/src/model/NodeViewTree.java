package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.ExceptionTracer;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.ExceptionTracerImpl;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

public class NodeViewTree {
    private final NodeView root;
    private final ExceptionTracer tracer;

    /**
     * Creates a tree, each node of which describes a unit of the file system.
     */
    public NodeViewTree(NodeView root) {
        this.root = root;
        this.tracer = new ExceptionTracerImpl();
    }

    public @Nullable NodeView root() {
        return this.root;
    }

    @SuppressWarnings("unused")
    public @NotNull ExceptionTracer exceptions() {
        return this.tracer;
    }

    @SuppressWarnings("unused")
    public ExceptionTracer buildRecursive() {
        return this.tracer;
    }
}
