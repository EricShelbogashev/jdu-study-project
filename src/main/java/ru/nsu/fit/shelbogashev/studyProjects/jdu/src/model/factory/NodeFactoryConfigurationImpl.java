package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.options.JduOptions;

import java.util.Collection;

/**
 * Supplies node handlers and jdu options to the factory.
 *
 * @param handlers list of required node handlers.
 */
public record NodeFactoryConfigurationImpl(JduOptions options,
                                           Collection<NodeHandler> handlers) implements NodeFactoryConfiguration {
}
