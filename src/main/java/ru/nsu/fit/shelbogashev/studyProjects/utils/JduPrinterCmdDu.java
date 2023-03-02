package ru.nsu.fit.shelbogashev.studyProjects.utils;

import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.Jdu;
import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.Node;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Formatter;

public class JduPrinterCmdDu implements JduPrinter {
    private final long align;
    private static final long DEFAULT_ALIGN = 128;
    public JduPrinterCmdDu() {
        this.align = DEFAULT_ALIGN;
    }

    public JduPrinterCmdDu(int align) {
        this.align = align;
    }

    @Override
    public void print(OutputStream stream, Node root) {
        if (root.children() == null) return;
        Formatter formatter = new Formatter();
        try {
            stream.write(
                    formatter.format(
                            "%-" + align + "s %6d\n",
                            root.path(),
                            root.size()).toString().getBytes()
            );
        } catch (IOException e) {
//                throw new RuntimeException(e);
        }
        for (Node node : root.children()) {
            print(stream, node);
        }
    }
}
