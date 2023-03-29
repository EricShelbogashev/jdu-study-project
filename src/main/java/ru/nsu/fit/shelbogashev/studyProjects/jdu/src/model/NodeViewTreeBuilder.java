package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.ExceptionTracer;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeFactory;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeFactoryConfiguration;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.exception.NodeViewTreeBuilderException;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;

public class NodeViewTreeBuilder {
    private final Path root;
    private final HashSet<Path> visited;
    private NodeFactory factory;
    private final ExceptionTracer tracer;

    private NodeViewTreeBuilder(@NotNull Path root) {
        this.root = root;
        this.visited = new HashSet<>();
        this.factory = null;
        this.tracer = new ExceptionTracer();
    }

    @NotNull
    public static NodeViewTreeBuilder of(@NotNull Path path) {
        return new NodeViewTreeBuilder(path);
    }

    /**
     * Creates a tree, each node of which describes a unit of the file system.
     *
     * @param configuration factory context which uses by node handlers to specify it.
     * @return tree wrapper that stores errors that occurred during its creation.
     * @throws NodeViewTreeBuilderException if configuration wasn't set.
     */
    public TreeBuilderResult build(@Nullable NodeFactoryConfiguration configuration) throws NodeViewTreeBuilderException {
        ExceptionTracer tracer = new ExceptionTracer();
        if (configuration == null) throw new NodeViewTreeBuilderException("configuration is required");
        this.factory = new NodeFactory(configuration);
        NodeView treeRoot = buildRecursively(this.root, -1);
        assert treeRoot != null;
        return new TreeBuilderResult(treeRoot, tracer);
    }

    private NodeView buildRecursively(Path path, int currentDepth) {
        if (currentDepth == factory.getConfiguration().options().depth()) return null;
        ArrayList<NodeView> children = null;

        try {
            Path realPath = path.toRealPath();
            if (visited.contains(realPath)) return factory.get(path, null, this.tracer);
            visited.add(realPath);
        } catch (IOException e) {
            this.tracer.put(e);
            return null;
        }

        if (Files.isSymbolicLink(path)
                && Boolean.FALSE.equals(factory.getConfiguration().options().symbolicLinkFollow())
        ) {
            return factory.get(path, null, tracer);
        }
        if (!Files.isDirectory(path)) {
            return factory.get(path, null, tracer);
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            children = new ArrayList<>();
            for (Path childPath : stream) {
                NodeView child = buildRecursively(childPath, currentDepth + 1);
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
