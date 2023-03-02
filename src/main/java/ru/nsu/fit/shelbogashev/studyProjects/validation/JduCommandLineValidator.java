package ru.nsu.fit.shelbogashev.studyProjects.validation;

import org.apache.commons.cli.CommandLine;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class JduCommandLineValidator {
    public static void validate(CommandLine commandLine) throws IllegalArgumentException {
        int len = commandLine.getArgs().length;

        if (len > 1) {
            throw new IllegalArgumentException("too much arguments");
        }
        if (len == 1) {
            Path path = Path.of(commandLine.getArgList().get(0));
            if (!Files.isDirectory(path)) {
                throw new IllegalArgumentException("expected directory");
            }
            if (Files.notExists(path)) {
                throw new IllegalArgumentException("path is not exists");
            }
        }

        try {
            if (commandLine.hasOption("depth")) {
                Integer.parseInt(commandLine.getOptionValue("depth"));
            }
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("expected integer value > 0 in depth");
        }
    }
}
