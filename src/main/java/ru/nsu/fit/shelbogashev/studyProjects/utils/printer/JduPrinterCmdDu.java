package ru.nsu.fit.shelbogashev.studyProjects.utils.printer;

import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.tree.Node;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Formatter;

public class JduPrinterCmdDu implements JduPrinter {
    private final long align;
    private static final long DEFAULT_PATH_ALIGN = 128;
    public JduPrinterCmdDu() {
        this(DEFAULT_PATH_ALIGN);
    }

    public JduPrinterCmdDu(long align) {
        this.align = align;
    }

    @Override
    public void print(OutputStream stream, Node root, long depth) {
        if (depth < 0) return;
        Formatter formatter = new Formatter();
        try {
            stream.write(
                    formatter.format(
                            "%-" + align + "s %6d%-10s%s\n",
                            root.path(),
                            root.size(),
                            root.isRelativeSize() ? "*" : "",
                            root.type()).toString().getBytes()
            );
        } catch (IOException ignored) {}
        if (root.children() == null) return;
        for (Node node : root.children().stream().sorted().toList()) {
            print(stream, node, depth - 1);
        }
    }
}
