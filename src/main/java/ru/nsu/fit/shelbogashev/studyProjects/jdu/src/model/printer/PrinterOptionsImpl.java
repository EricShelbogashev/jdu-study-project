package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer;

public record PrinterOptionsImpl(int limit,
                                 int depth,
                                 boolean symbolicLinkFollow) implements NodeViewTreePrinterOptions {
}
