package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.size;

/*
 * IEC 60027-2-2015
 * */
public class IECSizeFormatter implements SizeFormatter {
    @Override
    public String format(long size) {
        if (size / 1024 < 1024) return size / 1024.0 + "KiB";
        if (size / (1024 * 1024) < 1024) return size / (1024.0 * 1024) + "MiB";
        return size / (1024.0 * 1024 * 1024) + "GiB";
    }
}
