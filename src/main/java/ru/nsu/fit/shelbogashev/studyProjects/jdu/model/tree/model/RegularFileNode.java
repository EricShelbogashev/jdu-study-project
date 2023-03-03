package ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.model;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.exception.NodeRefreshException;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.api.Node;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.api.NodeHandler;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.fabric.FactoryContext;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.fabric.FactoryMethod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

@NodeHandler
public class RegularFileNode extends AbstractNode {

    protected RegularFileNode(Path path, Node parent) {
        super(path, parent);
    }

    @FactoryMethod
    public static RegularFileNode createRegularFileNode(Path path, Node parent, FactoryContext context) {
        if (!Files.isRegularFile(path)) return null;
        return new RegularFileNode(path, parent);
    }

    @Override
    public String type() {
        return "regular file";
    }

    @Override
    public void refreshChildren() throws NodeRefreshException {
    }

    @Override
    public void refreshSize() throws NodeRefreshException {
        this.isRelativeSize = false;
        this.lastRefresh = (new Date()).getTime();
        try {
            this.size = Files.size(path);
        } catch (IOException e) {
            throw new NodeRefreshException(e);
        }
    }

    @Override
    public boolean isRelativeSize() {
        return this.isRelativeSize;
    }

    @Override
    public String toString() {
        return path.toFile().getName();
    }
}
