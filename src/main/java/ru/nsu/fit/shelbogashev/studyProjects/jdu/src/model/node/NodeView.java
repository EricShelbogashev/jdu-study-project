package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.size.Size;

import java.nio.file.Path;
import java.util.Collection;

public interface NodeView extends Comparable<Node> {
    @NotNull
    Path path();

    @NotNull
    String type();

    @NotNull
    Boolean isRelativeSize();

    @NotNull
    Size size();

    @Nullable
    Collection<NodeView> children();

    @Override
    String toString();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
