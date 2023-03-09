package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.size.SizeFormatter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Formatter;

public class JduTreePrinterFlat extends AbstractJduTreePrinter {
    private final SizeFormatter sizeFormatter;

    public JduTreePrinterFlat(SizeFormatter formatter) {
        this.sizeFormatter = formatter;
    }

    @Override
    public void printNode(OutputStream stream, NodeView node) throws IOException {
        Formatter formatter = new Formatter();
        stream.write(
                formatter.format(
                        "%-" + align + "s %6s%-10s%s\n",
                        node.path(),
                        node.size().getString(this.sizeFormatter),
                        node.isRelativeSize() ? "*" : "",
                        node.type()).toString().getBytes()
        );
    }
}
