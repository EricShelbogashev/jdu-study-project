package ru.nsu.fit.shelbogashev.studyProjects.jdu.model.exception;

public class NodeFactoryException extends RuntimeException {
    public NodeFactoryException(String message) {
        super(message);
    }

    public NodeFactoryException(Throwable exception) {
        super(exception);
    }
}