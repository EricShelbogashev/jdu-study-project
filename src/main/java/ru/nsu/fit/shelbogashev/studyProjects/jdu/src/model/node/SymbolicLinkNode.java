package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.size.Size;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;

public class SymbolicLinkNode extends AbstractNode {
    public SymbolicLinkNode(Path path, Collection<NodeView> children, SymbolicLinkBehavior behaviour) throws IOException {
        super(path, behaviour == SymbolicLinkBehavior.LIKE_A_DIRECTORY ? children : null, AtomicType.SYMBOLIC_LINK);
        BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
        this.size = new Size(attributes.size());
    }

    @Override
    public @NotNull String specifiedType() {
        return "symbolic link file";
    }
}
