package ru.nsu.fit.shelbogashev.studyProjects.jdu;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.Jdu;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.options.JduOptions;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.options.exception.JduOptionsException;

public class Main {
    public static void main(String[] args) {
        try {
            JduOptions options = JduOptions.builder().byArgs(args).build();
            Jdu jdu = new Jdu(options);
            jdu.render();
        } catch (JduOptionsException e) {
            System.out.println("Caught exception: " + e);
            System.out.println(JduOptions.usage());
        }
    }
}