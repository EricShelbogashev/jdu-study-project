package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.formatter.Size;

import java.nio.file.Path;
import java.util.Collection;

/**
 * Implements the core functionality of the Node.
 */
public abstract class AbstractNode implements NodeView {

    protected Path path;
    protected Boolean isRelativeSize;
    protected Size size;
    protected Collection<NodeView> children;
    protected AtomicType type;

    public AbstractNode(Path path, Collection<NodeView> children, AtomicType type) {
        this.path = path;
        this.size = Size.SIZE_UNKNOWN;
        this.isRelativeSize = Boolean.TRUE;
        this.children = children;
        this.type = type;
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
    public int compareTo(@NotNull NodeView o) {
        if (this.equals(o)) return 0;
        if (o.size() == this.size()) return o.path().compareTo(this.path);
        return (int) (o.size().getValue() - this.size.getValue());
    }

    @Override
    public @NotNull AtomicType atomicType() {
        return type;
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

        return path.equals(that.path);
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
