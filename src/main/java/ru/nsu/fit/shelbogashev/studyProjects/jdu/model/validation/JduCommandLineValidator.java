package ru.nsu.fit.shelbogashev.studyProjects.jdu.model.validation;

import org.apache.commons.cli.CommandLine;

public class JduCommandLineValidator {
    public static void validate(CommandLine commandLine) throws IllegalArgumentException {
        int len = commandLine.getArgs().length;

        if (len > 1) {
            throw new IllegalArgumentException("too much arguments");
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
