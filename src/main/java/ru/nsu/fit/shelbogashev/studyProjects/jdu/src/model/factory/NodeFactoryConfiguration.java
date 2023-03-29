package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.options.JduOptions;

import java.util.Collection;

/**
 * Supplies node handlers to the factory.
 */
public interface NodeFactoryConfiguration {
    /**
     * Collection of required node handlers
     */
    Collection<NodeHandler> handlers();

    JduOptions options();
}
