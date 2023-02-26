package ru.nsu.fit.shelbogashev.studyProjects;

import org.apache.commons.cli.*;
import ru.nsu.fit.shelbogashev.studyProjects.model.Jdu;

public class Main {
    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        Jdu jdu = Jdu.getInstance();
        try {
            CommandLine cmd = parser.parse(jdu.getOptions(), args);
            jdu.execute(cmd);
        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("jdu", jdu.getOptions());
        }
    }
}