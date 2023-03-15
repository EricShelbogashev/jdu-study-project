package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.ExceptionTracer;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeFactory;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

import java.nio.file.Path;

public interface NodeViewTreeBuildRecursivelyAction {
    NodeView apply(Path path, NodeFactory factory, ExceptionTracer tracer);
}
