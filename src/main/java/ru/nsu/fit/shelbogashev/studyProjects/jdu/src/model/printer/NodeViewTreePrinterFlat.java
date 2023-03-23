package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.size.SizeFormatter;

import java.io.IOException;
import java.io.OutputStream;

@SuppressWarnings("unused")
public class NodeViewTreePrinterFlat extends AbstractNodeViewTreePrinter {
    private final SizeFormatter sizeFormatter;
    private final int align;

    public NodeViewTreePrinterFlat(SizeFormatter formatter, int align) {
        this.sizeFormatter = formatter;
        this.align = align;
    }

    @Override
    public void printNode(OutputStream stream, NodeView node) throws IOException {
        stream.write(
                String.format(
                        "%-" + align + "s %6s%-10s%s\n",
                        node.path(),
                        node.size().getString(this.sizeFormatter),
                        node.isRelativeSize() ? "*" : "",
                        node.specifiedType()).getBytes()
        );
    }
}
