package ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.model;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.exception.NodeRefreshException;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.api.Node;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.api.NodeHandler;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.fabric.FactoryContext;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.fabric.FactoryMethod;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.fabric.NodeFactory;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.fabric.Order;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

@Order
@NodeHandler
public class DirectoryNode extends AbstractNode {

    protected DirectoryNode(Path path, Node parent) {
        super(path, parent);
    }

    @FactoryMethod
        public static DirectoryNode createDirectory(Path path, Node parent, FactoryContext context) {
        if (!Files.isDirectory(path)) return null;
        return new DirectoryNode(path, parent);
    }

    @Override
    public String type() {
        return "directory";
    }

    @Override
    public void refreshChildren() throws NodeRefreshException {
        HashSet<Node> refreshedChildren = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.path())) {
            for (Path p : stream) {
                Node node = NodeFactory.instance().get(p, this);
                if (node != null) {
                    refreshedChildren.add(node);
                }
            }
            this.children = refreshedChildren;
            this.isRelativeSize = true;
        } catch (IOException e) {
            throw new NodeRefreshException(e);
        }
    }

    @Override
    public void refreshSize() throws NodeRefreshException {
        if (!this.isRelativeSize()) return;
        if (this.children == null) {
            refreshChildren();
        }

        size = 0;
        isRelativeSize = false;
        for (Node child : this.children) {
            child.refreshSize();
            size += child.size();
            if (child.isRelativeSize()) {
                this.isRelativeSize = true;
            }
        }
    }

    @Override
    public String toString() {
        return path.getName(path.getNameCount() - 1).toString();
    }
}

