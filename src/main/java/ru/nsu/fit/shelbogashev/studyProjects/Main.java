package ru.nsu.fit.shelbogashev.studyProjects;

import org.apache.commons.cli.*;
import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.Jdu;
import ru.nsu.fit.shelbogashev.studyProjects.utils.JduPrinterCmdDu;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        Jdu jdu = Jdu.getInstance();
        try {
            CommandLine cmd = parser.parse(jdu.getOptions(), args);
            jdu.load(cmd).print(new JduPrinterCmdDu(48));
        } catch (ParseException | URISyntaxException | IOException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("jdu", jdu.getOptions());
        }
    }
}