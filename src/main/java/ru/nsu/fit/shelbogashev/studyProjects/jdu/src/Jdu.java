package ru.nsu.fit.shelbogashev.studyProjects.jdu.src;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.NodeViewTree;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.NodeViewTreeBuilder;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeFactoryConfigurationImpl;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.handler.DirectoryNodeHandler;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.handler.RegularFileNodeHandler;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.handler.SymbolicLinkNodeHandler;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.handler.UnknownPathTypeNodeHandler;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer.NodeViewTreePrinter;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer.NodeViewTreePrinterOptionsImpl;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer.NodeViewTreePrinterTree;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.size.SizeFormatterIEC;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.options.JduOptions;

import java.io.OutputStream;
import java.util.Arrays;

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
        NodeViewTree tree = NodeViewTreeBuilder.of(options.path())
                .setConfiguration(new NodeFactoryConfigurationImpl(options, Arrays.asList(
                        new DirectoryNodeHandler(),
                        new RegularFileNodeHandler(),
                        new SymbolicLinkNodeHandler(),
                        new UnknownPathTypeNodeHandler()
                )
                ))
                .build();
        NodeView root = tree.root();
        NodeViewTreePrinter printer = new NodeViewTreePrinterTree(new SizeFormatterIEC());
        printer.printTo(stream, root, new NodeViewTreePrinterOptionsImpl(
                options.limit(),
                options.depth(),
                options.symbolicLinkFollow()
        ));
    }
}