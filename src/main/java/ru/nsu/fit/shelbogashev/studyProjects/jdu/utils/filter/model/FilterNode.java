package ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.filter.model;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.exception.NodeRefreshException;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.api.Node;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

public class FilterNode implements Node {
    private Path path;
    private String type;
    private ArrayList<FilterNode> children;
    private FilterNode parent;
    private long lastRefresh;
    private long size;
    private boolean isRelativeSize;

    private FilterNode(Node node, FilterNode parent) {
        this.type = node.type();
        this.path = node.path();
        this.parent = parent;
        this.lastRefresh = node.lastRefresh();
        this.size = node.size();
        this.isRelativeSize = node.isRelativeSize();

        if (node.children() == null) {
            children = null;
            return;
        }
        ArrayList<FilterNode> newChildren = new ArrayList<>();
        for (Node child : node.children()) {
            newChildren.add(new FilterNode(child, this));
        }
        this.children = newChildren;
    }

    public static FilterNode of(Node node) {
        return new FilterNode(node, null);
    }

    @Override
    public Path path() {
        return this.path;
    }

    @Override
    public String type() {
        return this.type;
    }

    @Override
    public Node parent() {
        return parentRaw();
    }

    public FilterNode parentRaw() {
        return this.parent;
    }

    @Override
    public boolean isRelativeSize() {
        return this.isRelativeSize;
    }

    @Override
    public long lastRefresh() {
        return this.lastRefresh;
    }

    @Override
    public long size() {
        return this.size;
    }

    @Override
    public void refreshChildren() throws NodeRefreshException {
    }

    @Override
    public void refreshSize() throws NodeRefreshException {
    }

    @Override
    public Collection<Node> children() {
        if (this.children == null) return null;
        return new ArrayList<>(this.children);
    }

    public Collection<FilterNode> childrenRaw() {
        return this.children;
    }

    @Override
    public void refreshAll() throws NodeRefreshException {
    }

    @Override
    public void refreshAllSilent() {
    }

    public void setRelativeSize(boolean relativeSize) {
        isRelativeSize = relativeSize;
    }

    public void setLastRefresh(long lastRefresh) {
        this.lastRefresh = lastRefresh;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setChildren(ArrayList<FilterNode> children) {
        this.children = children;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void setParent(FilterNode parent) {
        this.parent = parent;
    }

    @Override
    public int compareTo(@NotNull Node o) {
        return o.path().compareTo(this.path);
    }

    @Override
    public String toString() {
        return path.getName(path.getNameCount() - 1).toString();
    }
}
