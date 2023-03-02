package ru.nsu.fit.shelbogashev.studyProjects.model.jdu;

import java.nio.file.Path;
import java.util.TreeSet;

public interface Node {
    Path path();

    String type();

    TreeSet<Node> children();

    long lastRefresh();

    @Override
    String toString();
}
