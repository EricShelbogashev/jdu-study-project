package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.formatter;

public record TreeFormatter(int childAlign) {
    public TreeFormatter() {
        this(2);
    }
}
