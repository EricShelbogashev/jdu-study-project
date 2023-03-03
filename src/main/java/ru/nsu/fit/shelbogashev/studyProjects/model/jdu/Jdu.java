package ru.nsu.fit.shelbogashev.studyProjects.model.jdu;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.tree.FileSystemUnitPredicate;
import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.tree.Node;
import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.tree.NodeFactory;
import ru.nsu.fit.shelbogashev.studyProjects.utils.printer.JduPrinter;
import ru.nsu.fit.shelbogashev.studyProjects.utils.ReflectionUtils;
import ru.nsu.fit.shelbogashev.studyProjects.validation.JduCommandLineValidator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Jdu {
    private static Jdu INSTANCE;
    private static final long DEFAULT_DEPTH = 16;
    private final static Options options = new Options();
    private Node root;
    private CommandLine commandLine;

    private Jdu() {
        options.addOption("depth", true, "depth of recursion");
        options.addOption("L", false, "click through the symlinks");
        options.addOption("limit", true, "show most found files and/or directories");
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

        root = NodeFactory.instance().get(rootPath, null);
        root.refreshAllSilent();
        return this;
    }

    public void print(JduPrinter printer) {
        if (root != null) {
            long depth = -1;
            if (commandLine != null) {
                depth = commandLine.hasOption("depth") ? Long.parseLong(commandLine.getOptionValue("depth")) : depth;
            }
            printer.print(System.out, root, depth);
        }
    }

    public Options getOptions() {
        return options;
    }

}