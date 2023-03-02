package ru.nsu.fit.shelbogashev.studyProjects.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReflectionUtils {
    /**
     * Prototype of this function <a href="https://stackoverflow.com/a/58773038/12389741">is here</a>.
     *
     * @param packageName
     * @return
     */
    public static List<Class<?>> getClassesForDirectory(String packageName) throws URISyntaxException, IOException {
        String packagePath = Path.of(packageName).toString();
        URI packageRef = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(packagePath)).toURI();
        List<Class<?>> allClasses = new LinkedList<>();

        Path dir;
        if (packageRef.toString().startsWith("jar:")) {
            try {
                dir = FileSystems.getFileSystem(packageRef).getPath(packagePath);
            } catch (FileSystemNotFoundException e) {
                dir = FileSystems.newFileSystem(packageRef, Collections.emptyMap()).getPath(packagePath);
            }
        } else {
            dir = Paths.get(packageRef);
        }

        String ext = ".class";
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            stream.forEach(it -> {
                if (Files.isRegularFile(it)) {
                    try {
                        StringBuilder builder = new StringBuilder();
                        for (int i = 0; i < it.getNameCount(); i++) {
                            builder.append(it.getName(i));
                            if (i != it.getNameCount() - 1) {
                                builder.append('.');
                            }
                        }
                        String name = builder.substring(builder.indexOf(packageName), builder.length() - ext.length());
                        allClasses.add(Class.forName(name));
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allClasses;
    }

    static List<Class<?>> getClassesForDirectory(String packageName, Predicate<Class<?>> predicate) throws URISyntaxException, IOException {
        return getClassesForDirectory(packageName).stream().filter(predicate).collect(Collectors.toList());
    }

    public static List<Class<?>> getClassesForPackage(final String pkgName) throws IOException, URISyntaxException {
        final String pkgPath = pkgName.replace('.', '/');
        final URI pkg = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(pkgPath)).toURI();
        final ArrayList<Class<?>> allClasses = new ArrayList<Class<?>>();

        Path root;
        if (pkg.toString().startsWith("jar:")) {
            try {
                root = FileSystems.getFileSystem(pkg).getPath(pkgPath);
            } catch (final FileSystemNotFoundException e) {
                root = FileSystems.newFileSystem(pkg, Collections.emptyMap()).getPath(pkgPath);
            }
        } else {
            root = Paths.get(pkg);
        }

        final String extension = ".class";
        try (final Stream<Path> allPaths = Files.walk(root)) {
            allPaths.filter(Files::isRegularFile).forEach(file -> {
                try {
                    final String path = file.toString().replace('/', '.');
                    final String name = path.substring(path.indexOf(pkgName), path.length() - extension.length());
                    allClasses.add(Class.forName(name));
                } catch (final ClassNotFoundException | StringIndexOutOfBoundsException ignored) {
                }
            });
        }
        return allClasses;
    }

    public static List<Class<?>> getClassesForPackage(String packageName, Predicate<Class<?>> predicate) throws URISyntaxException, IOException {
        return getClassesForPackage(packageName).stream().filter(predicate).collect(Collectors.toList());
    }
}
