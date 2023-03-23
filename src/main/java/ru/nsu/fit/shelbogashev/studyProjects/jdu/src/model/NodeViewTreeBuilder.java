package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.ExceptionTracerImpl;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeFactory;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeFactoryConfiguration;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.exception.NodeViewTreeBuilderException;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

import java.nio.file.Path;

public class NodeViewTreeBuilder {
    private final Path root;
    private NodeFactoryConfiguration configuration = null;

    private NodeViewTreeBuilder(@NotNull Path root) {
        this.root = root;
    }

    @NotNull
    public static NodeViewTreeBuilder of(@NotNull Path path) {
        return new NodeViewTreeBuilder(path);
    }

    @NotNull
    public NodeViewTreeBuilder setConfiguration(@Nullable NodeFactoryConfiguration configuration) {
        this.configuration = configuration;
        return this;
    }

    /**
     * Creates a tree, each node of which describes a unit of the file system.
     *
     * @return tree wrapper that stores errors that occurred during its creation.
     * @throws NodeViewTreeBuilderException if configuration wasn't set.
     */
    public NodeViewTree build() throws NodeViewTreeBuilderException {
        ExceptionTracerImpl tracer = new ExceptionTracerImpl();
        // CR: two arguments in build
        if (configuration == null) throw new NodeViewTreeBuilderException("configuration is required");
        NodeFactory factory = new NodeFactory(configuration);
        NodeViewTreeBuildRecursivelyAction actionBuild = new NodeViewTreeBuildRecursivelyActionImpl(
                configuration.depth(),
                configuration.symbolicLinkFollow()
        );
        NodeView treeRoot = actionBuild.apply(root, factory, tracer);
        return new NodeViewTree(treeRoot, tracer);
    }


}
