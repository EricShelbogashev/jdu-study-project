package ru.nsu.fit.shelbogashev.studyProjects.jdu.model;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.api.Node;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.fabric.FactoryContext;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.fabric.NodeFactory;

import java.nio.file.Path;

public class JduTree {
    private FactoryContext context = null;
    private Path path;

    private JduTree(Path path) {
        this.path = path;
    }

    public static JduTree of(Path path) {
        return new JduTree(path);
    }

    public JduTree setContext(FactoryContext context) {
        this.context = context;
        return this;
    }

    public Node build() {
        return NodeFactory.instance().setContext(this.context).get(this.path, null);
    }
}
