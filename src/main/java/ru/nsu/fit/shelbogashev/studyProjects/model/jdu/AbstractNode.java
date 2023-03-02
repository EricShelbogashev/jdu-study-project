package ru.nsu.fit.shelbogashev.studyProjects.model.jdu;

import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.exception.NodeRefreshException;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Date;

public abstract class AbstractNode implements Node {
    protected Collection<Node> children;
    protected final Node parent;
    protected final Path path;
    protected boolean isRelativeSize;
    protected long size;
    protected long lastRefresh;

    protected AbstractNode(Path path, Node parent) {
        this.children = null;
        this.parent = parent;
        this.path = path;
        this.isRelativeSize = true;
        this.size = 0;
        this.lastRefresh = 0;
    }

    @Override
    public Path path() {
        return path;
    }

    @Override
    public Collection<Node> children() {
        return children;
    }

    public boolean isRelativeSize() {
        return true;
    }

    @Override
    public long lastRefresh() {
        return lastRefresh;
    }

    @Override
    public Node parent() {
        return parent;
    }

    @Override
    public void refreshAll() throws NodeRefreshException {
        lastRefresh = (new Date()).getTime();
        refreshChildren();
        refreshSize();
    }

    @Override
    public void refreshAllSilent() throws NodeRefreshException {
        try {
            refreshAll();
        } catch (NodeRefreshException e) {
            children = null;
            size = 0;
        }
    }

    @Override
    public long size() {
        return size;
    }

    @Override
    public int hashCode() {
        return this.path.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
