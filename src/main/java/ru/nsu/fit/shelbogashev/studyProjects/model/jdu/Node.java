package ru.nsu.fit.shelbogashev.studyProjects.model.jdu;

import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.exception.NodeRefreshException;

import java.nio.file.Path;
import java.util.Collection;

public interface Node {
    Path path();

    String type();

    Collection<Node> children();

    Node parent();

    boolean isRelativeSize();

    void refreshChildren() throws NodeRefreshException;

    void refreshSize() throws NodeRefreshException;

    void refreshAll() throws NodeRefreshException;

    void refreshAllSilent();

    long lastRefresh();

    long size();

    @Override
    String toString();

    @Override
    public int hashCode();

    @Override
    public boolean equals(Object o);
}
