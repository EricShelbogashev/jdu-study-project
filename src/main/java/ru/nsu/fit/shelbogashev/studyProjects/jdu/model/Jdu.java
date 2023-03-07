package ru.nsu.fit.shelbogashev.studyProjects.jdu.model;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.api.Node;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.api.SymbolicLinkBehaviour;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.fabric.FactoryContext;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.fabric.NodeFactory;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.validation.FileSystemUnitPredicate;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.validation.JduCommandLineValidator;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.ReflectionUtils;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.filter.api.TreeFilter;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.filter.model.TreeFilterDepth;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.filter.model.TreeFilterLimit;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.printer.api.JduPrinter;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.filter.model.FilterNode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

public final class Jdu {
    private static final long DEFAULT_DEPTH = 16;
    private static final long DEFAULT_LIMIT = Long.MAX_VALUE;
    private final static Options options = new Options();
    private static Jdu INSTANCE;
    private Node root;
    private CommandLine commandLine;

    private Jdu() {
        options.addOption(null, "depth", true, "depth of recursion");
        options.addOption("L", false, "click through the symlinks");
        options.addOption(null, "limit", true, "show most found files and/or directories");
        commandLine = null;
    }

    public static Jdu getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Jdu();
        }
        return INSTANCE;
    }

    public Jdu load(@NotNull CommandLine commandLine) throws URISyntaxException, IOException, IllegalArgumentException {
        JduCommandLineValidator.validate(commandLine);
        this.commandLine = commandLine;
        Path rootPath = this.commandLine.getArgs().length != 0 ? Paths.get(this.commandLine.getArgs()[0]) : Paths.get(".");

        NodeFactory nodeFactory = NodeFactory.instance();

        for (Class<?> unit : ReflectionUtils.getClassesForPackage(Jdu.class.getPackageName(), new FileSystemUnitPredicate())) {
            nodeFactory.registerNodeHandler((Class<Node>) unit);
        }

        FactoryContext context = new FactoryContext();
        context.put(
                "symbolLink.behaviour",
                commandLine.hasOption("-L")
                        ? SymbolicLinkBehaviour.LIKE_A_DIRECTORY
                        : SymbolicLinkBehaviour.LIKE_A_FILE
        );

        root = JduTree.of(rootPath).setContext(context).build();
        root.refreshAllSilent();

        ArrayList<TreeFilter> filters = new ArrayList<>();
        if (commandLine.hasOption("depth")) {
            filters.add(
                    new TreeFilterDepth(Long.parseLong(commandLine.getOptionValue("depth")))
            );
        }
        if (commandLine.hasOption("limit")) {
            filters.add(
                    new TreeFilterLimit(Long.parseLong(commandLine.getOptionValue("limit")))
            );
        }
        this.filter(filters);
        return this;
    }

    /***
     * Must be ordered.
     * @param filters
     */
    private void filter(Collection<TreeFilter> filters) {
        if (root != null) {
            FilterNode rootWrapper = FilterNode.of(root);
            for (TreeFilter filter : filters) {
                filter.apply(rootWrapper);
            }
            root = rootWrapper;
        }
    }

    public void print(JduPrinter printer) {
        if (root != null) {
            printer.printAll(System.out, root);
        }
    }

    public Options getOptions() {
        return options;
    }

}