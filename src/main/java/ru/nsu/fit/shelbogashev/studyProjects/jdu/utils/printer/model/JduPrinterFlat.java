package ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.printer.model;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.api.Node;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.printer.api.JduPrinter;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.printer.model.AbstractPrinter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Formatter;

public class JduPrinterFlat extends AbstractPrinter implements JduPrinter {

    @Override
    public void print(OutputStream stream, Node node) throws IOException {
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
