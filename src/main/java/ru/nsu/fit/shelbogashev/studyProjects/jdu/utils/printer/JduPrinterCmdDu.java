package ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.printer;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.api.Node;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.Formatter;
import java.util.TreeSet;

public class JduPrinterCmdDu implements JduPrinter {
    private static final long DEFAULT_PATH_ALIGN = 128;
    private final long align;

    public JduPrinterCmdDu() {
        this(DEFAULT_PATH_ALIGN);
    }

    public JduPrinterCmdDu(long align) {
        this.align = align;
    }

    @Override
    public void print(OutputStream stream, Node root, long depth, Limit limit) {
        if (limit.equals(Limit.UNLIMITED)) {
            printAll(stream, root, depth);
            return;
        }
        printWeight(stream, root, depth, limit);
    }

    private void printWeight(
            OutputStream stream,
            Node root,
            long depth,
            Limit limit) {
        TreeSet<Node> orderedSet = new TreeSet<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return (int) (o2.size() - o1.size());
            }
        });
        accumulateNodes(root, orderedSet, limit, depth);
        orderedSet.forEach(it -> {
            try {
                this.print(stream, it);
            } catch (IOException ignored) {
            }
        });
    }

    private void accumulateNodes(Node root, TreeSet<Node> orderedSet, Limit limit, long depth) {
        if (depth < 0) return;
        if (root.children() == null) {
            return;
        }
        int num = 0;
        for (Node node : root.children().stream().sorted().toList()) {
            if (num == limit.value()) {
                break;
            }
            accumulateNodes(node, orderedSet, limit, depth - 1);
            num++;
        }
        if (orderedSet.add(root) && orderedSet.size() > limit.value()) {
            orderedSet.remove(orderedSet.last());
        }
    }

    private void printAll(OutputStream stream, Node root, long depth) {
        if (depth < 0) return;
        try {
            print(stream, root);
        } catch (IOException ignored) {
        }
        if (root.children() == null) return;
        for (Node node : root.children().stream().sorted().toList()) {
            printAll(stream, node, depth - 1);
        }
    }

    private void print(OutputStream stream, Node node) throws IOException {
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
