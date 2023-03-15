package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.options;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public interface JduOptions {
    @NotNull Integer depth();

    @NotNull Boolean symbolicLinkFollow();

    @NotNull Integer limit();

    @NotNull Path path();
}
