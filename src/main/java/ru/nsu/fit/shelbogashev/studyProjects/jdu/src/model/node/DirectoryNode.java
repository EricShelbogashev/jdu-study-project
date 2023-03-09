package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Collection;


public class DirectoryNode extends AbstractNode {

    public DirectoryNode(Path path, Collection<NodeView> children) {
        super(path, children);
    }

    @Override
    public @NotNull String type() {
        return "directory";
    }
}
