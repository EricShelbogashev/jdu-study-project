package ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.printer.model;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.api.Node;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.printer.api.JduPrinter;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.printer.model.AbstractPrinter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Formatter;

public class JduPrinterTree extends AbstractPrinter implements JduPrinter {
    protected static final long DEFAULT_PATH_ALIGN = 2;

    public JduPrinterTree() {
        super(DEFAULT_PATH_ALIGN);
    }

    public JduPrinterTree(long align) {
        super(align);
    }

    @Override
    public void print(OutputStream stream, Node node) throws IOException {
        Formatter formatter = new Formatter();
        double size;
        String sizeMdf;
        if (node.size() > 1024 * 1024 * 1024) {
            size = node.size() / 1024.0 * 1024 * 1024;
            sizeMdf = "Gb";
        } else if (node.size() > 1024 * 1024) {
            size = node.size() / 1024.0 * 1024;
            sizeMdf = "Mb";
        } else if (node.size() > 1024) {
            size = node.size() / 1024.0;
            sizeMdf = "Kb";
        } else {
            size = node.size();
            sizeMdf = "b";
        }
        stream.write(
                formatter.format(
                        " ".repeat((int) (align * (node.path().getNameCount() - 1))) + "%s [%s%.2f %s]\n",
                        node.path().getName(node.path().getNameCount() - 1),
                        node.isRelativeSize() ? "*" : "",
                        size,
                        sizeMdf).toString().getBytes()
        );
    }
}
