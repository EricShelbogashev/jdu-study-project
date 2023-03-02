package ru.nsu.fit.shelbogashev.studyProjects.model.jdu.node;

import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.*;
import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.exception.NodeRefreshException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

@NodeHandler
public class RegularFileNode extends AbstractNode {

    @FabricMethod
    public static RegularFileNode createRegularFileNode(Path path, Node parent) {
        if (!Files.isRegularFile(path)) return null;
        return new RegularFileNode(path, parent);
    }

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
