package ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.fabric;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.api.Node;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.exception.NodeFactoryException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.*;

public class NodeFactory {
    private static final Package SEARCH_PACKAGE = NodeFactory.class.getPackage();
    private static NodeFactory INSTANCE;
    public final Collection<Pair> units;
    private FactoryContext context = null;

    private NodeFactory() {
        units = new TreeSet<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                if (o2.order == o1.order) {
                    if (o1.equals(o2)) return 0;
                    return 1;
                }
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

    /* Можно ли как-нибудь сделать конфигурацию path, но спрятать этот метод? */
    public void registerNodeHandler(Class<? extends Node> handler) {
        Optional<Method> fabricMethod = Arrays.stream(handler.getDeclaredMethods()).filter(
                it -> it.isAnnotationPresent(FactoryMethod.class)
        ).findAny();

        if (fabricMethod.isEmpty()) {
            throw new NodeFactoryException(
                    new IllegalArgumentException("handler is not valid: excepted produce method")
            );
        }

        Method method;
        try {
            method = handler.getDeclaredMethod(fabricMethod.get().getName(), Path.class, Node.class, FactoryContext.class);
        } catch (NoSuchMethodException e) {
            throw new NodeFactoryException(e);
        }

        Optional<Annotation> orderAnnotation = Arrays.stream(handler.getAnnotations()).filter(
                it -> it.annotationType() == Order.class
        ).findAny();

        int order = orderAnnotation.map(annotation -> ((Order) annotation).order()).orElse(Integer.MAX_VALUE);

        units.add(new Pair(method, order));
    }

    public NodeFactory setContext(FactoryContext context) {
        this.context = context;
        return this;
    }

    public Node get(Path path, Node parent) {
        for (Pair pair : units) {
            Node node = null;
            try {
                node = (Node) pair.method.invoke(null, path, parent, this.context);
            } catch (IllegalAccessException | InvocationTargetException ignored) {
            }
            if (node != null) {
                return node;
            }
        }
        throw new NodeFactoryException("undefined path reference");
    }

    private record Pair(Method method, int order) {
    }
}