package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.AtomicType;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer.exception.NodeViewTreePrinterException;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.size.SizeFormatter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class NodeViewTreePrinterTree extends AbstractNodeViewTreePrinter {
    // CR: rename
    protected static final int DEFAULT_PATH_ALIGN = 2;
    // CR: add PrinterFormatter with SizeFormatter and TreeFormatter inside
    private final SizeFormatter sizeFormatter;
    private final int align;
    private int offset;

    public NodeViewTreePrinterTree(SizeFormatter formatter) {
        this(formatter, DEFAULT_PATH_ALIGN);
    }


    public NodeViewTreePrinterTree(SizeFormatter formatter, int align) {
        this.sizeFormatter = formatter;
        this.offset = 0;
        this.align = align;
    }

    @Override
    public void printTo(OutputStream stream, NodeView root, NodeViewTreePrinterOptions options) throws NodeViewTreePrinterException {
        offset = root.path().getNameCount();
        super.printTo(stream, root, options);
    }

    @Override
    public void printNode(OutputStream stream, NodeView node) throws IOException {
        String filename = String.valueOf(node.path().getFileName());
        if (node.atomicType() == AtomicType.SYMBOLIC_LINK) {
            filename += " -> " + Files.readSymbolicLink(node.path());
        }
        stream.write(
                String.format(
                        " ".repeat(align * (node.path().getNameCount() - offset)) + "%s%s" + " ".repeat(align) + "[%s]\n",
                        node.atomicType() == AtomicType.DIRECTORY ? "/" : "",
                        filename,
                        node.size().getString(this.sizeFormatter)).getBytes()
        );
    }
}
