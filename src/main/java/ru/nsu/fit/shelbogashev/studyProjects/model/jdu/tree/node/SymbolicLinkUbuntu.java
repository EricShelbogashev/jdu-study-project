package ru.nsu.fit.shelbogashev.studyProjects.model.jdu.tree.node;

import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.exception.NodeRefreshException;
import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.tree.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

@Order
@NodeHandler
public class SymbolicLinkUbuntu extends AbstractNode {

    @FabricMethod
    public static SymbolicLinkUbuntu createRegularFileNode(Path path, Node parent) {
        if (!Files.isSymbolicLink(path)) return null;
        return new SymbolicLinkUbuntu(path, parent);
    }

    protected SymbolicLinkUbuntu(Path path, Node parent) {
        super(path, parent);
    }

    @Override
    public String type() {
        return "ubuntu symbolic link";
    }

    @Override
    public void refreshChildren() throws NodeRefreshException {}

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
