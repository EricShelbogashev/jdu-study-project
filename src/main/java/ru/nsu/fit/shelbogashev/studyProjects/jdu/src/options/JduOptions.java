package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.options;

import org.apache.commons.cli.Options;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

/**
 * Individual Option could be null, but Options must not be null.
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

    @NotNull
    public static String usage() {
        if (USAGE_MESSAGE == null) initOptions();
        return USAGE_MESSAGE;
    }

    protected static Options commandLineOptions() {
        if (OPTIONS == null) initOptions();
        return OPTIONS;
    }

    private static void initOptions() {
        OPTIONS = new Options();
        OPTIONS.addOption(null, "depth", true, "depth of recursion");
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

