package ru.nsu.fit.shelbogashev.studyProjects.utils;

import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.Jdu;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Formatter;

public class JduPrinterCmdDu implements JduPrinter {

    private final int align;
    public JduPrinterCmdDu() {
        this.align = 128;
    }

    public JduPrinterCmdDu(int align) {
        this.align = align;
    }

    @Override
    public void print(OutputStream stream, Jdu.JduNode root) {
        if (root.getChildren() == null) return;
        Formatter formatter = new Formatter();
        try {
            stream.write(
                    formatter.format(
                            "%-" + align + "s %6d\n",
                            root.getPath(),
                            root.getSize()).toString().getBytes()
            );
        } catch (IOException e) {
//                throw new RuntimeException(e);
        }
        for (Jdu.JduNode node : root.getChildren()) {
            print(stream, node);
        }
    }
}
