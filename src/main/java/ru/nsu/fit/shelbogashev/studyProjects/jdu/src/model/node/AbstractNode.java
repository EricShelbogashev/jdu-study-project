package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.size.Size;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;

/**
 * Implements the core functionality of the Node.
 * It is necessary to redefine the type for {@link NodeView#type()} and add a {@link ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeHandler handler} for this node.
 */
public abstract class AbstractNode implements Node {
    protected Path path;
    protected Boolean isRelativeSize;
    protected Size size;
    protected Collection<NodeView> children;

    public AbstractNode(Path path, Collection<NodeView> children) {
        this.path = path;
        this.size = Size.SIZE_UNKNOWN;
        this.isRelativeSize = Boolean.TRUE;
        if (children != null) {
            long size = 0;
            for (NodeView node : children) {
                size += node.size().getValue();
                if (node.isRelativeSize()) isRelativeSize = Boolean.TRUE;
            }
            this.size = new Size(size);
        }
        this.children = children;
    }

    @Override
    public @NotNull Path path() {
        return this.path;
    }

    @Override
    public @NotNull Boolean isRelativeSize() {
        return this.isRelativeSize;
    }

    @Override
    public @NotNull Size size() {
        return this.size;
    }

    @Override
    public Collection<NodeView> children() {
        return children;
    }

    @Override
    public int compareTo(@NotNull Node o) {
        if (this.equals(o)) return 0;
        if (o.size() == this.size()) return o.path().compareTo(this.path);
        return (int) (o.size().getValue() - this.size.getValue());
    }

    @Override
    public void setPath(Path path) {
        this.path = path;
    }

    @Override
    public void setIsRelativeSize(Boolean isRelativeSize) {
        this.isRelativeSize = isRelativeSize;
    }

    @Override
    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public void setChildren(Collection<NodeView> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "AbstractNode{" +
                "path=" + path +
                ", isRelativeSize=" + isRelativeSize +
                ", size=" + size +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractNode that = (AbstractNode) o;

        if (isRelativeSize != that.isRelativeSize) return false;
        if (!path.equals(that.path)) return false;
        if (!size.equals(that.size)) return false;
        return Objects.equals(children, that.children);
    }

    @Override
    public int hashCode() {
        int result = path.hashCode();
        result = 31 * result + (isRelativeSize ? 1 : 0);
        result = 31 * result + size.hashCode();
        result = 31 * result + (children != null ? children.hashCode() : 0);
        return result;
    }
}
