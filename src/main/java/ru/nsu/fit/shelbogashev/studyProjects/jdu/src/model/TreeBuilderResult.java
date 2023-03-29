package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.ExceptionTracer;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

public record TreeBuilderResult(@NotNull NodeView root, @NotNull ExceptionTracer exceptions) {
}
