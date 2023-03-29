package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.formatter;

/**
 * Formats bytes using the ISU standard.
 */
@SuppressWarnings("ALL")
public class SizeFormatterISU implements SizeFormatter {
    private static String getFormatString(double value, String suffix) {
        return String.format("%.2f %s", value, suffix);
    }

    /**
     * @param size size in bytes.
     * @return string representation of a size in Bytes, KB, MB, GB.
     */
    @Override
    public String format(long size) {
        if (size < 1000) return getFormatString(size, "B");
        if (size / 1000 < 1000) return getFormatString(size / 1000.0, "KB");
        if (size / (1000 * 1000) < 1000) return getFormatString(size / (1000.0 * 1000), "MB");
        return getFormatString(size / (1000.0 * 1000 * 1000), "GB");
    }
}
