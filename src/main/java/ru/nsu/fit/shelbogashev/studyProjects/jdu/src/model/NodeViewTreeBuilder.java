package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model;

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

    private NodeViewTreeBuilder(Path root) {
        this.root = root;
    }

    public static NodeViewTreeBuilder of(Path path) {
        return new NodeViewTreeBuilder(path);
    }

    public NodeViewTreeBuilder setContext(NodeFactoryContext context) {
        this.context = context;
        return this;
    }

    public NodeView build() {
        ExceptionTracerImpl tracer = new ExceptionTracerImpl();
        NodeFactoryConfiguration configuration = new NodeFactoryConfiguration(Arrays.asList(
                new DirectoryNodeHandler(),
                new RegularFileNodeHandler(),
                new SymbolicLinkNodeHandler()
        ));
        // TODO: some actions.
        NodeFactory factory = new NodeFactory(configuration, context);
        return buildRecursively(root, factory, tracer);
    }

    private NodeView buildRecursively(Path path, NodeFactory factory, ExceptionTracer tracer) {
        ArrayList<NodeView> children = null;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(root)) {
            children = new ArrayList<>();
            for (Path childPath : stream) {
                NodeView child = buildRecursively(childPath, factory, tracer);
                if (child != null) {
                    children.add(child);
                }
            }
        } catch (IOException ignored) {
        }
        return factory.get(path, children, tracer);
    }
}
