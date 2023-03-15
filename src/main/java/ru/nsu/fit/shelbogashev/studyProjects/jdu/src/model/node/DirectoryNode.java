package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.size.Size;

import java.nio.file.Path;
import java.util.Collection;


public class DirectoryNode extends AbstractNode {

    public DirectoryNode(Path path, Collection<NodeView> children) {
        super(path, children, AtomicType.DIRECTORY);
        if (children != null) {
            long size = 0;
            for (NodeView node : children) {
                size += node.size().getValue();
                if (node.isRelativeSize()) isRelativeSize = Boolean.TRUE;
            }
            this.size = new Size(size);
        }
    }

    @Override
    public @NotNull String specifiedType() {
        return "directory";
    }
}
