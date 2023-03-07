package ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.api;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.exception.NodeRefreshException;

import java.nio.file.Path;
import java.util.Collection;

public interface Node extends Comparable<Node> {
    Path path();

    String type();

    Node parent();

    boolean isRelativeSize();

    long lastRefresh();

    long size();

    String toString();

    public int hashCode();

    public boolean equals(Object o);

    void refreshChildren() throws NodeRefreshException;

    void refreshSize() throws NodeRefreshException;

    Collection<Node> children();

    void refreshAll() throws NodeRefreshException;

    void refreshAllSilent();
}
