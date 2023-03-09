package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.options;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.options.exception.JduOptionsException;

import java.nio.file.Path;

public class JduOptionsBuilder {
    public static DefaultParser DEFAULT_PARSER = new DefaultParser();
    public static Integer DEFAULT_DEPTH = Integer.MAX_VALUE;
    public static Integer DEFAULT_LIMIT = Integer.MAX_VALUE;
    public static Boolean DEFAULT_SYMBOLIC_LINK_FOLLOW = Boolean.FALSE;
    public static Path DEFAULT_PATH = Path.of(System.getProperty("user.dir"));

    public String[] args;
    public CommandLineParser parser;
    public Integer depth;
    public Boolean symbolicLinkFollow;
    public Integer limit;
    public Path path;

    public JduOptionsBuilder() {
        this.args = null;
        this.parser = null;
        this.depth = null;
        this.symbolicLinkFollow = null;
        this.limit = null;
        this.path = null;
    }

    /**
     * Used for initialization by command line input.
     * @param args  flags and values from {@link JduOptions#usage()}.
     * @return this.
     */
    @SuppressWarnings("unused")
    @NotNull
    public JduOptionsBuilder byArgs(@Nullable String[] args) {
        this.args = args;
        return this;
    }

    /**
     * @param path root directory to build from.
     * @return this.
     */
    @SuppressWarnings("unused")
    @NotNull
    public JduOptionsBuilder path(@Nullable Path path) {
        this.path = path;
        return this;
    }

    /**
     * @param parser    parser to use to interpret input from {@link JduOptionsBuilder#byArgs(String[])}
     * @return this.
     */
    @SuppressWarnings("unused")
    @NotNull
    public JduOptionsBuilder withParser(@Nullable CommandLineParser parser) {
        this.parser = parser;
        return this;
    }

    /**
     * @param depth maximum nesting level relative to start directory.
     * @return this.
     */
    @SuppressWarnings("unused")
    @NotNull
    public JduOptionsBuilder depth(int depth) {
        this.depth = depth;
        return this;
    }

    /**
     * @param symbolicLinkFollow    whether there will be child directories of symbolic links in the render.
     * @return this.
     */
    @SuppressWarnings("unused")
    @NotNull
    public JduOptionsBuilder symbolicLinkFollow(@Nullable Boolean symbolicLinkFollow) {
        this.symbolicLinkFollow = symbolicLinkFollow;
        return this;
    }

    /**
     * @param limit the maximum number of child elements of a folder, when rendering.
     * @return this.
     */
    @SuppressWarnings("unused")
    @NotNull
    public JduOptionsBuilder limit(@Nullable Integer limit) {
        this.limit = limit;
        return this;
    }

    /**
     * @return {@link JduOptions}   instance with data entered by the user.
     *                      Anything not entered is filled in with default values.
     * @throws JduOptionsException  wrapper for all sorts of internal errors.
     */
    @SuppressWarnings("unused")
    @NotNull
    public JduOptions build() throws JduOptionsException {
        if (this.parser == null) this.parser = DEFAULT_PARSER;

        if (this.args != null) {
            try {
                CommandLine commandLine = this.parser.parse(JduOptions.commandLineOptions(), args);
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

        if (this.depth == null) this.depth = DEFAULT_DEPTH;
        if (this.symbolicLinkFollow == null) this.symbolicLinkFollow = DEFAULT_SYMBOLIC_LINK_FOLLOW;
        if (this.limit == null) this.limit = DEFAULT_LIMIT;
        if (this.path == null) this.path = DEFAULT_PATH;
        return new JduOptions(this);
    }
}
