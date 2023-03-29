package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.options;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.options.exception.JduOptionsException;

import java.nio.file.Path;

public class JduOptionsBuilder {
    public static final DefaultParser DEFAULT_PARSER = new DefaultParser();
    public static final int DEFAULT_DEPTH = 64;
    public static final int DEFAULT_LIMIT = Integer.MAX_VALUE;
    public static final boolean DEFAULT_SYMBOLIC_LINK_FOLLOW = Boolean.FALSE;
    public static final Path DEFAULT_PATH = Path.of(".");

    public CommandLineParser parser = DEFAULT_PARSER;
    public int depth = DEFAULT_DEPTH;
    public boolean symbolicLinkFollow = DEFAULT_SYMBOLIC_LINK_FOLLOW;
    public int limit = DEFAULT_LIMIT;
    public Path path = DEFAULT_PATH;

    // CR: one more ctor with parser argument
    public JduOptionsBuilder() {
    }

    @SuppressWarnings("unused")
    public JduOptionsBuilder(CommandLineParser parser) {
        this.parser = parser;
    }

    /**
     * @param path root directory to build from.
     * @return this.
     */
    @SuppressWarnings("unused")
    @NotNull
    public JduOptionsBuilder path(@NotNull Path path) {
        this.path = path;
        return this;
    }

    /**
     * @param depth maximum nesting level relative to start directory.
     * @return this.
     */
    @SuppressWarnings("unused")
    @NotNull
    public JduOptionsBuilder depth(int depth) {
        if (depth < 0) {
            throw new JduOptionsException("Depth must be greater or equals than 0.");
        }
        this.depth = depth;
        return this;
    }

    /**
     * @param symbolicLinkFollow whether there will be child directories of symbolic links in the render.
     * @return this.
     */
    @SuppressWarnings("unused")
    @NotNull
    public JduOptionsBuilder symbolicLinkFollow(boolean symbolicLinkFollow) {
        this.symbolicLinkFollow = symbolicLinkFollow;
        return this;
    }

    /**
     * @param limit the maximum number of child elements of a folder, when rendering.
     * @return this.
     */
    @SuppressWarnings("unused")
    @NotNull
    public JduOptionsBuilder limit(int limit) {
        if (limit < 0) {
            throw new JduOptionsException("Limit must be greater or equals than 0.");
        }
        this.limit = limit;
        return this;
    }

    /**
     * @return {@link JduOptionsImpl}   instance with data entered by the user.
     * Anything not entered is filled in with default values.
     * @throws JduOptionsException wrapper for all sorts of internal errors.
     */
    @SuppressWarnings("unused")
    @NotNull
    public JduOptions buildByArgs(String[] args) throws JduOptionsException {
        if (args != null) {
            try {
                CommandLine commandLine = this.parser.parse(JduOptionsImpl.commandLineOptions(), args);
                if (commandLine.getArgList().size() == 1) {
                    this.path = Path.of(commandLine.getArgList().get(0));
                } else if (commandLine.getArgList().size() == 0) {
                    this.path = DEFAULT_PATH;
                } else {
                    throw new IllegalArgumentException("Too much directory arguments");
                }
                if (commandLine.hasOption("depth")) this.depth = Integer.parseInt(commandLine.getOptionValue("depth"));
                if (commandLine.hasOption("L")) this.symbolicLinkFollow = Boolean.TRUE;
                if (commandLine.hasOption("limit")) this.limit = Integer.parseInt(commandLine.getOptionValue("limit"));
            } catch (ParseException | IllegalArgumentException e) {
                throw new JduOptionsException(e);
            }
        }
        return new JduOptionsImpl(this);
    }

    @SuppressWarnings("unused")
    @NotNull
    public JduOptions build(String[] args) throws JduOptionsException {
        return new JduOptionsImpl(this);
    }
}
