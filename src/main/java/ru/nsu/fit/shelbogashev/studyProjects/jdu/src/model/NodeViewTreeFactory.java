package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.ExceptionTracer;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.ExceptionTracerImpl;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeFactory;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeFactoryConfiguration;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;

public class NodeViewTreeFactory {
    private final int depth;
    private final NodeFactory factory;
    private final boolean symbolicLinkFollow;

    public NodeViewTreeFactory(int depth, boolean symbolicLinkFollow, NodeFactoryConfiguration configuration) {
        this.depth = depth;
        this.factory = new NodeFactory(configuration);
        this.symbolicLinkFollow = symbolicLinkFollow;
    }

    public NodeViewTree get(Path path) {
        ExceptionTracer tracer = new ExceptionTracerImpl();
        NodeView root = buildRecursively(path, factory, tracer, 0);
        return new NodeViewTree(root);
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

        if (Files.isSymbolicLink(path) && Boolean.FALSE.equals(symbolicLinkFollow)) {
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
        } catch (IOException e) {
            tracer.put(e);
        }
        return factory.get(path, children, tracer);
    }
}
