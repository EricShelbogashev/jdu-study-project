package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer.exception.JduTreePrinterException;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.size.SizeFormatter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Formatter;

public class JduTreePrinterTree extends AbstractJduTreePrinter {
    protected static final long DEFAULT_PATH_ALIGN = 2;
    private final SizeFormatter sizeFormatter;
    private int offset;

    public JduTreePrinterTree(SizeFormatter formatter) {
        super(DEFAULT_PATH_ALIGN);
        this.sizeFormatter = formatter;
        this.offset = 0;
    }

    @Override
    public void printTo(OutputStream stream, NodeView root) throws JduTreePrinterException {
        offset = root.path().getNameCount();
        super.printRecursive(stream, root);
    }

    @Override
    public void printNode(OutputStream stream, NodeView node) throws IOException {
        Formatter formatter = new Formatter();
        stream.write(
                formatter.format(
                        " ".repeat((int) (align * (node.path().getNameCount() - offset))) + "%s%s" + " ".repeat((int) align) +"[%s]\n",
                        node.type().contains("directory") ? "/" : "",
                        node.path().getName(node.path().getNameCount() - 1),
                        node.size().getString(this.sizeFormatter)).toString().getBytes()
        );
    }
}
