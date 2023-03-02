package ru.nsu.fit.shelbogashev.studyProjects.model.jdu.node;

import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.FileSystemUnit;
import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.Node;

import java.nio.file.Path;
import java.util.TreeSet;

@FileSystemUnit
public class DirectoryNode implements Node {
    @Override
    public Path path() {
        return null;
    }

    @Override
    public String type() {
        return null;
    }

    @Override
    public TreeSet<Node> children() {
        return null;
    }

    @Override
    public long lastRefresh() {
        return 0;
    }
}
