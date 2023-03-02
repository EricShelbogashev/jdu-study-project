package ru.nsu.fit.shelbogashev.studyProjects.model.jdu;

import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.node.DirectoryNode;

import java.lang.annotation.Annotation;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;

public class Tree {
    private Node root;

    private Tree() {
    }


    public static Tree of(Path root, Path handlersLocation) {
        Tree tree = new Tree();

        Class<?> klass = null;
        try {
            System.out.println(klass.getName());
            System.out.println(Arrays.toString(klass.getInterfaces()));
        } catch (Exception e) {
            System.out.println(e);
        }
//        Annotation[] annotations = klass.getAnnotations();
//        klass.getI
//        this.root = ;

        return tree;
    }
}
