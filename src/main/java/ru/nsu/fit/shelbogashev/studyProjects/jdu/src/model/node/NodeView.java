package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.formatter.Size;

import java.nio.file.Path;
import java.util.Collection;

public interface NodeView extends Comparable<NodeView> {
    @NotNull
    Path path();

    @NotNull
    AtomicType atomicType();

    @NotNull
    Boolean isRelativeSize();

    @NotNull
    Size size();

    Collection<NodeView> children();

    @Override
    String toString();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
