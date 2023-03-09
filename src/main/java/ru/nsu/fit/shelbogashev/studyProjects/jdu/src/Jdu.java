package ru.nsu.fit.shelbogashev.studyProjects.jdu.src;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.NodeViewTreeBuilder;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeFactoryContext;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.options.JduOptions;

import java.io.OutputStream;

public final class Jdu {
    JduOptions options;

    public Jdu(JduOptions options) {
        this.options = options;
    }

    public void render() {
        renderTo(System.out);
    }

    public void renderTo(OutputStream stream) {
        NodeFactoryContext context = new NodeFactoryContext(options);
        NodeView tree = NodeViewTreeBuilder.of(options.getPath())
                .setContext(context)
                .build();

    }
}