package ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.model;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.exception.NodeRefreshException;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.api.Node;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.api.NodeHandler;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.api.SymbolicLinkBehaviour;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.fabric.FactoryContext;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.fabric.FactoryMethod;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.fabric.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Order
@NodeHandler
public class SymbolicLink extends DirectoryNode {

    private final SymbolicLinkBehaviour behaviour;

    protected SymbolicLink(Path path, Node parent) {
        this(path, parent, SymbolicLinkBehaviour.LIKE_A_DIRECTORY);
    }
    protected SymbolicLink(Path path, Node parent, SymbolicLinkBehaviour behaviour) {
        super(path, parent);
        this.behaviour = behaviour;
    }

    @FactoryMethod
    public static SymbolicLink createDirectory(Path path, Node parent, FactoryContext context) {
        if (!Files.isSymbolicLink(path)) return null;
        if (context.contains("symbolLink.behaviour")) {
            return new SymbolicLink(
                    path,
                    parent,
                    (SymbolicLinkBehaviour) context.get("symbolLink.behaviour")
            );
        }

        return new SymbolicLink(path, parent);
    }

    @Override
    public void refreshChildren() throws NodeRefreshException {
        if (behaviour.equals(SymbolicLinkBehaviour.LIKE_A_DIRECTORY)) {
            super.refreshChildren();
        }
    }

    @Override
    public void refreshSize() throws NodeRefreshException {
        if (behaviour.equals(SymbolicLinkBehaviour.LIKE_A_FILE)) {
            try {
                this.size = Files.size(this.path);
            } catch (IOException e) {
                throw new NodeRefreshException(e);
            }
        } else {
            super.refreshSize();
        }
    }

    @Override
    public String type() {
        return "ubuntu symbolic link";
    }
}

