package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.options;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;

/**
 * Command line options wrapper for jdu. Represents features of jdu.
 */
public class JduOptions {
    private static String USAGE_MESSAGE;
    private static Options OPTIONS;
    private final Integer depth;
    private final Boolean symbolicLinkFollow;
    private final Integer limit;
    private final Path path;

    public JduOptions(JduOptionsBuilder builder) {
        this.depth = builder.depth;
        this.symbolicLinkFollow = builder.symbolicLinkFollow;
        this.limit = builder.limit;
        this.path = builder.path;
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

    protected static Options commandLineOptions() {
        if (OPTIONS == null) initOptions();
        return OPTIONS;
    }

    private static void initOptions() {
        OPTIONS = new Options();
        OPTIONS.addOption("d", "depth", true, "depth of recursion");
        OPTIONS.addOption("L", false, "click through the symlinks");
        OPTIONS.addOption(null, "limit", true, "show most found files and/or directories");
    }

    @Nullable
    public Integer getDepth() {
        return depth;
    }

    @Nullable
    public Boolean getSymbolicLinkFollow() {
        return symbolicLinkFollow;
    }

    @Nullable
    public Integer getLimit() {
        return limit;
    }

    @NotNull
    public Path getPath() {
        return path;
    }
}

