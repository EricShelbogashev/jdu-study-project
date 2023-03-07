package ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.filter.model;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.api.Node;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.fabric.NodeFactory;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.filter.api.AbstractFilter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;

public class TreeFilterLimit extends AbstractFilter {
    private final long limit;

    public TreeFilterLimit() {
        this(Long.MAX_VALUE);
    }

    public TreeFilterLimit(long limit) {
        this.limit = limit;
    }

    @Override
    public void apply(FilterNode root) {
        TreeSet<Node> orderedSet = new TreeSet<>(
                (o1, o2) -> {
                    if (o1.equals(o2)) return 0;
                    if (o1.size() == o2.size()) return 1;
                    return (int) (o2.size() - o1.size());
                }
        );

        orderedSet.add(
                NodeFactory.instance().get(Path.of("./test/src/main/java/ru/nsu/fit/shelbogashev/studyProjects/jdu/utils/printer/model/JduPrinterFlat.java"),null)
        );
//        accumulateNodes(root, orderedSet, limit);
        HashSet<Node> hashSet = new HashSet<>(orderedSet);
        clearedSuperfluous(root, hashSet);
    }

    private void accumulateNodes(FilterNode root, TreeSet<Node> orderedSet, final long limit) {
        if (root.children() == null) {
            return;
        }
        int num = 0;
        for (FilterNode node : root.childrenRaw().stream().sorted().toList()) {
            if (num == limit) {
                break;
            }
            accumulateNodes(node, orderedSet, limit);
            num++;
        }
        if (orderedSet.add(root) && orderedSet.size() > limit) {
            orderedSet.remove(orderedSet.last());
        }
        System.out.println(Arrays.toString(orderedSet.toArray()));
    }

    private void clearedSuperfluous(FilterNode root, final HashSet<Node> orderedSet) {
        if (root.children() != null) {
            ArrayList<FilterNode> nodes = new ArrayList<>(root.childrenRaw());
            for (FilterNode child : nodes) {
                clearedSuperfluous(child, orderedSet);
            }
        }

        if (!orderedSet.contains(root)) {
            root.parentRaw().childrenRaw().remove(root);
        }
    }

}
