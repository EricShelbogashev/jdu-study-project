package ru.nsu.fit.shelbogashev.studyProjects.jdu;

import org.apache.commons.cli.*;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.Jdu;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.printer.model.JduPrinterFlat;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.printer.model.JduPrinterTree;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        Jdu jdu = Jdu.getInstance();
        try {
            CommandLine cmd = parser.parse(jdu.getOptions(), args);
            jdu.load(cmd).print(new JduPrinterFlat());
//            jdu.load(cmd).print(new JduPrinterTree(2));
        } catch (ParseException | URISyntaxException | IOException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("jdu", jdu.getOptions());
        }
    }
}