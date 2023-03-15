package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.options.JduOptions;

import java.nio.file.Path;
import java.util.Collection;

/**
 * Supplies node handlers and jdu options to the factory.
 *
 * @param handlers list of required node handlers.
 */
public record NodeFactoryConfigurationImpl(JduOptions options,
                                           Collection<NodeHandler> handlers) implements NodeFactoryConfiguration {
    @Override
    public @NotNull Integer depth() {
        return options.depth();
    }

    @Override
    public @NotNull Boolean symbolicLinkFollow() {
        return options.symbolicLinkFollow();
    }

    @Override
    public @NotNull Integer limit() {
        return options.limit();
    }

    @Override
    public @NotNull Path path() {
        return options.path();
    }
}
