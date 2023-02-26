package ru.nsu.fit.shelbogashev.studyProjects.model;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import ru.nsu.fit.shelbogashev.studyProjects.contract.Tree;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class Jdu {
    private static Jdu INSTANCE;
    private final Options options = new Options();

    private Jdu() {
        options.addOption("depth", true, "depth of recursion");
        options.addOption("L", false, "click through the symlinks");
        options.addOption("limit", true, "show most found files and/or directories");
    }

    public static Jdu getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Jdu();
        }
        return INSTANCE;
    }

    public void execute(CommandLine commandLine) {
        System.out.println(commandLine.toString());
        Option[] sessionOptions = commandLine.getOptions();
        // Добавить проверку на длину массива
        Path dir = Paths.get(commandLine.getArgList().get(0));
//        System.out.println(Files.isDirectory(dir));
    }

    public Options getOptions() {
        return options;
    }

}