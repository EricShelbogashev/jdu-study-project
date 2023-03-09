package ru.nsu.fit.shelbogashev.studyProjects.jdu.src;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.NodeViewTree;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.NodeViewTreeBuilder;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeFactoryContext;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.filter.JduTreeFilterDepth;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.filter.JduTreeFilterLimit;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer.NodeViewTreePrinter;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer.NodeViewTreePrinterTree;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.size.SizeFormatterIEC;
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

    /**
     * Prints the filesystem fingerprint to the stream.
     *
     * @param stream Stream to output the fingerprint in string format.
     */
    public void renderTo(OutputStream stream) {
        NodeFactoryContext context = new NodeFactoryContext(options);
        NodeViewTree tree = NodeViewTreeBuilder.of(options.getPath())
                .setContext(context)
                .build();
        NodeView root = tree.root();
        if (options.getDepth() != null) root = new JduTreeFilterDepth(options.getDepth()).apply(root);
        if (options.getLimit() != null) root = new JduTreeFilterLimit(options.getLimit()).apply(root);
        NodeViewTreePrinter printer = new NodeViewTreePrinterTree(new SizeFormatterIEC());
        printer.printTo(stream, root);
    }
}