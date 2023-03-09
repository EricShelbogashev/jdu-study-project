package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.handler;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.ExceptionTracer;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeFactoryContext;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeHandler;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.Order;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.Node;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.RegularFileNode;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.size.Size;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public class RegularFileNodeHandler implements NodeHandler {

    @Order(order = 0)
    @Override
    public Node createNode(Path path, Collection<NodeView> children, NodeFactoryContext context, ExceptionTracer exceptionTracer) {
        if (!Files.isRegularFile(path)) return null;
        try {
            return new RegularFileNode(path, children);
        } catch (IOException | SecurityException e) {
            exceptionTracer.put(e);
            return new RegularFileNode(path, children, Size.SIZE_UNKNOWN);
        }
    }
}
