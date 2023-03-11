package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.handler;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.ExceptionTracer;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeFactoryContext;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeHandler;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.AbstractNode;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.Node;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

import java.nio.file.Path;
import java.util.Collection;

public class UnknownPathTypeNodeHandler implements NodeHandler {
    @Override
    public Node createNode(@NotNull Path path, Collection<NodeView> children, NodeFactoryContext context, @NotNull ExceptionTracer exceptionTracer) {
        return new AbstractNode(path, null) {
            @Override
            public @NotNull String type() {
                return "unknown";
            }
        };
    }

    @Override
    public int order() {
        return Integer.MAX_VALUE;
    }
}