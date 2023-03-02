package ru.nsu.fit.shelbogashev.studyProjects.model.jdu.node;

import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.AbstractNode;
import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.NodeHandler;
import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.Node;
import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.Order;
import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.exception.NodeRefreshException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.TreeSet;

@NodeHandler
public class RegularFileNode extends AbstractNode {

    protected RegularFileNode(Path path, Node parent) {
        super(path, parent);
    }

    @Override
    public String type() {
        return null;
    }

    @Override
    public void refreshChildren() throws NodeRefreshException {}

    @Override
    public void refreshSize() throws NodeRefreshException {
        this.lastRefresh = (new Date()).getTime();
        try {
            this.size = Files.size(path);
        } catch (IOException e) {
            throw new NodeRefreshException(e);
        }
    }
}
