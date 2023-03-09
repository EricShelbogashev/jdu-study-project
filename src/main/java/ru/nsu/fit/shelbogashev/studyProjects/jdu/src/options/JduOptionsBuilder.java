package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.options;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.jetbrains.annotations.NotNull;
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

    @NotNull
    public JduOptionsBuilder byArgs(String[] args) {
        this.args = args;
        return this;
    }

    @NotNull
    public JduOptionsBuilder path(Path path) {
        this.path = path;
        return this;
    }

    @NotNull
    public JduOptionsBuilder withParser(CommandLineParser parser) {
        this.parser = parser;
        return this;
    }

    @NotNull
    public JduOptionsBuilder depth(int depth) {
        this.depth = depth;
        return this;
    }

    @NotNull
    public JduOptionsBuilder symbolicLinkFollow(Boolean symbolicLinkFollow) {
        this.symbolicLinkFollow = symbolicLinkFollow;
        return this;
    }

    @NotNull
    public JduOptionsBuilder limit(Integer limit) {
        this.limit = limit;
        return this;
    }

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
