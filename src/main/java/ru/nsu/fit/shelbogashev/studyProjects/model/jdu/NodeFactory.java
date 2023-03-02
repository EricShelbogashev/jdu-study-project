package ru.nsu.fit.shelbogashev.studyProjects.model.jdu;

import jdk.jfr.MetadataDefinition;
import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.exception.NodeFactoryException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.*;

public class NodeFactory {
    private final Collection<Pair> units;
    private static NodeFactory INSTANCE;
    private static final Package SEARCH_PACKAGE = NodeFactory.class.getPackage();

    private record Pair(Method method, int order) {}

    private NodeFactory() {
        units = new TreeSet<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return o2.order - o1.order;
            }
        });
    }

    public static NodeFactory instance() {
        if (INSTANCE == null) {
            INSTANCE = new NodeFactory();
        }
        return INSTANCE;
    }

    public void registerNodeHandler(Class<? extends Node> handler) {
        Optional<Method> fabricMethod = Arrays.stream(handler.getDeclaredMethods()).filter(
                it -> it.isAnnotationPresent(MetadataDefinition.class)
        ).findAny();

        if (fabricMethod.isEmpty()) {
            throw new NodeFactoryException(
                    new IllegalArgumentException("handler is not valid: excepted produce method")
            );
        }

        Optional<Annotation> orderAnnotation = Arrays.stream(handler.getAnnotations()).filter(
                it -> it.annotationType() == Order.class
        ).findAny();

        int order = orderAnnotation.map(annotation -> ((Order) annotation).order()).orElse(Integer.MAX_VALUE);

        units.add(new Pair(fabricMethod.get(), order));
    }

    public Node get(Path path, Node parent) {
        for (Pair pair: units) {
            Node node = null;
            try {
                node = (Node) pair.method.invoke(pair, parent);
            } catch (IllegalAccessException | InvocationTargetException ignored) {
            }
            if (node != null) {
                return node;
            }
        }
        throw new NodeFactoryException("undefined path reference");
    }
}