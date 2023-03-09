package ru.nsu.fit.shelbogashev.studyProjects.jdu.src;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.NodeViewTreeBuilder;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeFactoryContext;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.filter.JduTreeFilterDepth;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.filter.JduTreeFilterLimit;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer.JduTreePrinterFlat;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer.JduTreePrinter;
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

    public void renderTo(OutputStream stream) {
        NodeFactoryContext context = new NodeFactoryContext(options);
        NodeView tree = NodeViewTreeBuilder.of(options.getPath())
                .setContext(context)
                .build();
        if (options.getDepth() != null) tree = new JduTreeFilterDepth(options.getDepth()).apply(tree);
        if (options.getLimit() != null) tree = new JduTreeFilterLimit(options.getLimit()).apply(tree);
        JduTreePrinter printer = new JduTreePrinterFlat(new SizeFormatterIEC());
        printer.printTo(stream, tree);
    }
}