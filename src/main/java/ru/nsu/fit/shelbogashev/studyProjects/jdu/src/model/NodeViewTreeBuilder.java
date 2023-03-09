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
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class NodeViewTreeBuilder {
    private final Path root;
    private NodeFactoryContext context = null;

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
        NodeView treeRoot = buildRecursively(root, factory, tracer);
        return new NodeViewTree(treeRoot, tracer);
    }

    private NodeView buildRecursively(Path path, NodeFactory factory, ExceptionTracer tracer) {
        ArrayList<NodeView> children = null;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            children = new ArrayList<>();
            for (Path childPath : stream) {
                NodeView child = buildRecursively(childPath, factory, tracer);
                children.add(child);
            }
        } catch (IOException ignored) {
        }
        try {
            return factory.get(path.toRealPath(), children, tracer);
        } catch (IOException e) {
            tracer.put(e);
            return factory.get(path, children, tracer);
        }
    }
}
