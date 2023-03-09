package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Formatter;

public class FlatJduTreePrinter extends AbstractJduTreePrinter {

    @Override
    public void printNode(OutputStream stream, NodeView node) throws IOException {
        Formatter formatter = new Formatter();
        stream.write(
                formatter.format(
                        "%-" + align + "s %6d%-10s%s\n",
                        node.path(),
                        node.size(),
                        node.isRelativeSize() ? "*" : "",
                        node.type()).toString().getBytes()
        );
    }
}
