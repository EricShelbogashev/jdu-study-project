package ru.nsu.fit.shelbogashev.studyProjects.jdu;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.Jdu;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.options.JduOptions;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.options.JduOptionsImpl;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.options.exception.JduOptionsException;

public class Main {
    public static void main(String[] args) {
        try {
            JduOptions options = JduOptionsImpl.builder().buildByArgs(args);
            Jdu jdu = new Jdu(options);
            jdu.render();
        } catch (JduOptionsException e) {
            System.err.println("Caught exception: " + e);
            System.err.println(JduOptionsImpl.usage());
        }
    }
}