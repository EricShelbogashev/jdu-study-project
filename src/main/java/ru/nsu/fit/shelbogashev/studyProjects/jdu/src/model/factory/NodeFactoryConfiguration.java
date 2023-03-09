package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory;

import java.util.Collection;

/**
 * Supplies node handlers to the factory.
 * @param handlers  list of required node handlers.
 */
public record NodeFactoryConfiguration(Collection<NodeHandler> handlers) {
}
