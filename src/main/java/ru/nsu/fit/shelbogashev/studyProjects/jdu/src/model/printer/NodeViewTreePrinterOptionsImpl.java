package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer;

// CR: rename
public record NodeViewTreePrinterOptionsImpl(int limit,
                                             int depth,
                                             boolean symbolicLinkFollow) implements NodeViewTreePrinterOptions {
}
