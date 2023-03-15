package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.size;

import org.jetbrains.annotations.Range;

/**
 * Wrapper per file system unit size, supports formatted representation.
 */
public class Size {
    public static Size SIZE_UNKNOWN = new Size(0) {
        @Override
        public String getString(SizeFormatter formatter) {
            return "unknown";
        }
    };
    private final long value;

    public Size(@Range(from = 0, to = Long.MAX_VALUE) long value) {
        this.value = value;
    }

    /**
     * @return size.
     */
    public long getValue() {
        return value;
    }

    /**
     * @return formatted representation of {@link Size#getValue()}.
     */
    public String getString(SizeFormatter formatter) {
        return formatter.format(this.value);
    }
}
