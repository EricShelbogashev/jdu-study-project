package ru.nsu.fit.shelbogashev.studyProjects.model.jdu.exception;

public class NodeFactoryException extends RuntimeException {
    public NodeFactoryException(String message) {
        super(message);
    }

    public NodeFactoryException(Throwable exception) {
        super(exception);
    }
}