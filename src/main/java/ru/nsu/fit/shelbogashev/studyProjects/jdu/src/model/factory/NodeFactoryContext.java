package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.options.JduOptions;

/**
 * Gives factory access to jdu options.
 *
 * @param jduOptions actual {@link ru.nsu.fit.shelbogashev.studyProjects.jdu.src.Jdu Jdu} settings.
 */
public record NodeFactoryContext(JduOptions jduOptions) {
}
