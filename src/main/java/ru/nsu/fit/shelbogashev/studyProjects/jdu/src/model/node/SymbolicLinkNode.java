package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.size.Size;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public class SymbolicLinkNode extends AbstractNode {
    private final SymbolicLinkBehavior behaviour;

    public SymbolicLinkNode(Path path, Collection<NodeView> children, SymbolicLinkBehavior behaviour) throws IOException {
        super(path, behaviour == SymbolicLinkBehavior.LIKE_A_DIRECTORY ? children : null);
        this.behaviour = behaviour;
        if (behaviour == SymbolicLinkBehavior.LIKE_A_FILE) {
            this.size = new Size(Files.size(path));
        }
    }

    @Override
    public @NotNull String type() {
        return "symbolic link file";
    }
}
