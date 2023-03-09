package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.handler;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.ExceptionTracer;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeFactoryContext;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeHandler;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.DirectoryNode;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.Node;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public class DirectoryNodeHandler implements NodeHandler {
    @Override
    public Node createNode(Path path, Collection<NodeView> children, NodeFactoryContext context, ExceptionTracer exceptionTracer) {
        if (!Files.isDirectory(path)) return null;
        if (Files.isSymbolicLink(path)) return null;
        return new DirectoryNode(path, children);
    }
}
