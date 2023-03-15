package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.exception;

public class NodeViewTreeBuilderException extends RuntimeException {
    @SuppressWarnings("unused")
    public NodeViewTreeBuilderException(Throwable throwable) {
        super(throwable);
    }

    public NodeViewTreeBuilderException(String message) {
        super(message);
    }
}
