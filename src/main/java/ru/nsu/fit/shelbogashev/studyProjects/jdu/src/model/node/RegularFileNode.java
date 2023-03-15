package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.size.Size;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public class RegularFileNode extends AbstractNode {

    public RegularFileNode(Path path, Collection<NodeView> children) throws IOException {
        this(path, children, new Size(Files.size(path)));
    }

    public RegularFileNode(Path path, Collection<NodeView> children, Size size) {
        super(path, children, AtomicType.REGULAR_FILE);
        this.size = size;
    }

    @Override
    public @NotNull String specifiedType() {
        return "regular file";
    }
}
