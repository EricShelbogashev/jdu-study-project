package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.options;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;

/**
 * Command line options wrapper for jdu. Represents features of jdu.
 */
public record JduOptionsImpl(Integer depth, Boolean symbolicLinkFollow, Integer limit,
                             Path path) implements JduOptions {
    private static String USAGE_MESSAGE;
    private static Options OPTIONS;

    public JduOptionsImpl(JduOptionsBuilder builder) {
        this(builder.depth, builder.symbolicLinkFollow, builder.limit, builder.path);
    }

    public static JduOptionsBuilder builder() {
        return new JduOptionsBuilder();
    }

    /**
     * @return a hint message for using jdu.
     */
    @NotNull
    public static String usage() {
        if (USAGE_MESSAGE == null) {
            initOptions();
            StringWriter strOut = new StringWriter();
            PrintWriter printWriter = new PrintWriter(strOut);
            new HelpFormatter().printHelp(
                    printWriter,
                    120,
                    "jdu",
                    null,
                    OPTIONS,
                    2,
                    2,
                    null
            );
            USAGE_MESSAGE = strOut.toString();
        }
        return USAGE_MESSAGE;
    }

    static Options commandLineOptions() {
        if (OPTIONS == null) initOptions();
        return OPTIONS;
    }

    private static void initOptions() {
        OPTIONS = new Options();
        OPTIONS.addOption("d", "depth", true, "depth of recursion");
        OPTIONS.addOption("L", false, "click through the symlinks");
        OPTIONS.addOption(null, "limit", true, "show most found files and/or directories");
    }

    @Override
    public @NotNull Integer depth() {
        return depth;
    }

    @Override
    public @NotNull Boolean symbolicLinkFollow() {
        return symbolicLinkFollow;
    }

    @Override
    public @NotNull Integer limit() {
        return limit;
    }

    @Override
    public @NotNull Path path() {
        return path;
    }
}

