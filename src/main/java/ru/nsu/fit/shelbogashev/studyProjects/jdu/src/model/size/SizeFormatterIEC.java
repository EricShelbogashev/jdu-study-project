package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.size;

/*
 * IEC 60027-2-2015
 * */
public class SizeFormatterIEC implements SizeFormatter {
    private static String getFormatString(double value, String suffix) {
        return String.format("%.2f %s", value, suffix);
    }

    @Override
    public String format(long size) {
        if (size < 1024) return getFormatString(size, "B");
        if (size / 1024 < 1024) return getFormatString(size / 1024.0, "KiB");
        if (size / (1024 * 1024) < 1024) return getFormatString(size / (1024.0 * 1024), "MiB");
        return getFormatString(size / (1024.0 * 1024 * 1024), "GiB");
    }
}