package ru.nsu.fit.shelbogashev.studyProjects.model.jdu;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.shelbogashev.studyProjects.utils.JduPrinter;
import ru.nsu.fit.shelbogashev.studyProjects.utils.ReflectionUtils;
import ru.nsu.fit.shelbogashev.studyProjects.validation.JduCommandLineValidator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.TreeSet;

public final class Jdu {
    private static Jdu INSTANCE;
    private final static Options options = new Options();
    private JduNode root;

    public static class JduNode implements Comparable<JduNode> {
        private TreeSet<JduNode> children;
        private final Path path;
        private long size;
//        private long lastRefresh = -1;

        private JduNode(@NotNull Path path) {
            this.path = path;
            renderNode(-1);
        }

        private JduNode(Path path, boolean lazy, int depth) {
            this.path = path;
            if (!lazy) {
                renderNode(depth);
            }
        }

        public Path getPath() {
            return path;
        }

        public long getSize() {
            return size;
        }

        public TreeSet<JduNode> getChildren() {
            return children;
        }

        void renderNode(int depth) {
            if (depth == 0) {
                return;
            }
//            lastRefresh = (new Date()).getTime();
            this.children = new TreeSet<JduNode>();

            if (Files.isRegularFile(this.path)) {
                try {
                    size = Files.size(path);
                } catch (IOException e) {
//                    throw new RuntimeException(e);
                }
                return;
            }

            if (Files.isDirectory(this.path)) {
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
                    for (Path p : stream) {
                        JduNode node = new JduNode(
                                p,
                                false,
                                depth == -1 ? depth : depth - 1
                        );
                        children.add(
                                node
                        );
                        size += node.size;
                    }
                } catch (IOException e) {
//                throw new RuntimeException(e);
                }
            }
        }

        @Override
        public int compareTo(@NotNull Jdu.JduNode o) {
            return (int) (o.size - this.size);
        }

//        public long getLastRefresh() {
//            return lastRefresh;
//        }
    }

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

    public Jdu load(@NotNull CommandLine commandLine) throws URISyntaxException, IOException, IllegalArgumentException {
        JduCommandLineValidator.validate(commandLine);

        Path rootPath = commandLine.getArgs() == null ? Paths.get(commandLine.getArgs()[0]) : Paths.get(".");
        ReflectionUtils.getClassesForPackage(this.getClass().getPackage().getName(), new FileSystemUnitPredicate()).forEach(System.out::println);


//        ReflectionUtils.getClassesForDirectory("ru.nsu.fit.shelbogashev.studyProjects.model.jdu.node");
//        Tree fileSystem = Tree.of(
//                rootPath, this.getClass().getPackage() + ".node"
//        );
//        int depth = commandLine.hasOption("depth") ?
//                Integer.parseInt(commandLine.getOptionValue("depth")) : -1;
//        this.root = new JduNode(rootPath, false, depth);
        return this;
    }

    public void print(JduPrinter printer) {
//        printer.print(System.out, root);
    }

    public Options getOptions() {
        return options;
    }

}