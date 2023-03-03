package ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.model;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.exception.NodeRefreshException;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.api.Node;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Date;
import java.util.Formatter;

public abstract class AbstractNode implements Node {
    protected final Node parent;
    protected final Path path;
    protected Collection<Node> children;
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

    @Override
    public String toString() {
        return (new Formatter()).format(
                "[%6d]" + " ".repeat(this.path().getNameCount() * 2) + "%s\n",
                this.size,
                this.path().getName(this.path().getNameCount() - 1)
        ).toString();
    }

    @Override
    public int compareTo(@NotNull Node o) {
        return (int) (o.size() - this.size);
    }
}
