package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.*;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.handler.DirectoryNodeHandler;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.handler.RegularFileNodeHandler;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.handler.SymbolicLinkNodeHandler;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class NodeViewTreeBuilder {
    private static final int DEFAULT_DEPTH = 64;
    private final Path root;
    private NodeFactoryContext context = null;
    private int depth;

    private NodeViewTreeBuilder(@NotNull Path root) {
        this.root = root;
    }

    @NotNull
    public static NodeViewTreeBuilder of(@NotNull Path path) {
        return new NodeViewTreeBuilder(path);
    }

    @NotNull
    public NodeViewTreeBuilder setContext(@Nullable NodeFactoryContext context) {
        this.context = context;
        return this;
    }

    /**
     * Creates a tree, each node of which describes a unit of the file system.
     *
     * @return tree wrapper that stores errors that occurred during its creation.
     */
    public NodeViewTree build() {
        ExceptionTracerImpl tracer = new ExceptionTracerImpl();
        NodeFactoryConfiguration configuration = new NodeFactoryConfiguration(Arrays.asList(
                new DirectoryNodeHandler(),
                new RegularFileNodeHandler(),
                new SymbolicLinkNodeHandler()
        ));
        NodeFactory factory = new NodeFactory(configuration, context);
        depth = context.jduOptions().getDepth() == null ? DEFAULT_DEPTH : context.jduOptions().getDepth();
        NodeView treeRoot = buildRecursively(root, factory, tracer);
        return new NodeViewTree(treeRoot, tracer);
    }

    private NodeView buildRecursively(Path path, NodeFactory factory, ExceptionTracer tracer) {
        return buildRecursively(path, factory, tracer, 0);
    }

    private NodeView buildRecursively(Path path, NodeFactory factory, ExceptionTracer tracer, int currentDepth) {
        if (currentDepth == depth) return null;
        ArrayList<NodeView> children = null;

        try {
            path = path.toRealPath(LinkOption.NOFOLLOW_LINKS);
        } catch (IOException e) {
            tracer.put(e);
            return null;
        }

        if (Files.isSymbolicLink(path) && Boolean.FALSE.equals(context.jduOptions().getSymbolicLinkFollow())) {
            return factory.get(path, null, tracer);
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            children = new ArrayList<>();
            for (Path childPath : stream) {
                NodeView child = buildRecursively(childPath, factory, tracer, currentDepth + 1);
                if (child != null) {
                    children.add(child);
                }
            }
        } catch (IOException ignored) {
        }
        return factory.get(path, children, tracer);
    }
}
