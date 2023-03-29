package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.ExceptionTracer;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeFactory;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;

public class NodeViewTreeBuildRecursivelyActionImpl implements NodeViewTreeBuildRecursivelyAction {
    private final int depth;
    private final boolean symbolicLinkFollow;
    private final HashSet<Path> visited;

    public NodeViewTreeBuildRecursivelyActionImpl(int depth, boolean symbolicLinkFollow) {
        this.depth = depth;
        this.symbolicLinkFollow = symbolicLinkFollow;
        this.visited = new HashSet<>();
    }

    @Override
    public NodeView apply(Path path, NodeFactory factory, ExceptionTracer tracer) {
        return buildRecursively(path, factory, tracer, 0);
    }

    // CR: move to fields arguments that do not change
    private NodeView buildRecursively(Path path, NodeFactory factory, ExceptionTracer tracer, int currentDepth) {
        if (currentDepth == depth) return null;
        ArrayList<NodeView> children = null;

        try {
            Path realPath = path.toRealPath();
            if (visited.contains(realPath)) return factory.get(path, null, tracer);
            visited.add(realPath);
        } catch (IOException e) {
            tracer.put(e);
            return null;
        }

        if (Files.isSymbolicLink(path) && Boolean.FALSE.equals(symbolicLinkFollow)) {
            return factory.get(path, null, tracer);
        }
        if (!Files.isDirectory(path)) {
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