package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.size.Size;

import java.nio.file.Path;
import java.util.Collection;

public interface Node extends Comparable<Node>, NodeView {
    // CR: not used
    void setPath(Path path);

    void setIsRelativeSize(Boolean isRelativeSize);

    void setSize(Size size);

    void setChildren(Collection<NodeView> children);

    @Override
    String toString();
}
