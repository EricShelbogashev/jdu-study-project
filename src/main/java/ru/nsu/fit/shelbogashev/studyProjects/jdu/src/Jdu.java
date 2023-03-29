package ru.nsu.fit.shelbogashev.studyProjects.jdu.src;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.NodeViewTreeBuilder;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.TreeBuilderResult;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.NodeFactoryConfigurationImpl;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.formatter.PrinterFormatter;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.formatter.SizeFormatterIEC;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.formatter.TreeFormatter;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.handler.DirectoryNodeHandler;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.handler.RegularFileNodeHandler;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.handler.SymbolicLinkNodeHandler;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.handler.UnknownPathTypeNodeHandler;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer.NodeViewTreePrinter;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer.NodeViewTreePrinterTree;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer.PrinterOptionsImpl;
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
        TreeBuilderResult tree = NodeViewTreeBuilder.of(options.path())
                .build(new NodeFactoryConfigurationImpl(options, Arrays.asList(
                        new DirectoryNodeHandler(),
                        new RegularFileNodeHandler(),
                        new SymbolicLinkNodeHandler(),
                        new UnknownPathTypeNodeHandler()
                )
                ));
        tree.exceptions().getList().forEach(System.err::println);
        NodeView root = tree.root();
        NodeViewTreePrinter printer = new NodeViewTreePrinterTree(
                new PrinterFormatter(
                        new SizeFormatterIEC(),
                        new TreeFormatter()
                )
        );
        printer.printTo(stream, root, new PrinterOptionsImpl(
                options.limit(),
                options.depth(),
                options.symbolicLinkFollow()
        ));
    }
}