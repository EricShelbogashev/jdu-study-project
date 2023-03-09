package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory.exception;

public class NodeFactoryException extends RuntimeException {
    public NodeFactoryException(Throwable throwable) {
        super(throwable);
    }

    public NodeFactoryException(String message) {
        super(message);
    }
}
