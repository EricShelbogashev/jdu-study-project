package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.ExceptionTracer;
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

    /**
     * Creates a tree, each node of which describes a unit of the file system.
     *
     * @return tree wrapper that stores errors that occurred during its creation.
     * @param configuration factory context which uses by node handlers to specify it.
     * @throws NodeViewTreeBuilderException if configuration wasn't set.
     */
    public TreeBuilderResult build(@Nullable NodeFactoryConfiguration configuration) throws NodeViewTreeBuilderException {
        ExceptionTracer tracer = new ExceptionTracer();
        if (configuration == null) throw new NodeViewTreeBuilderException("configuration is required");
        NodeFactory factory = new NodeFactory(configuration);
        NodeViewTreeBuildRecursivelyAction actionBuild = new NodeViewTreeBuildRecursivelyActionImpl(
                configuration.options().depth(),
                configuration.options().symbolicLinkFollow()
        );
        NodeView treeRoot = actionBuild.apply(root, factory, tracer);
        return new TreeBuilderResult(treeRoot, tracer);
    }


}
