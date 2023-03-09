package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.size;

import org.jetbrains.annotations.Range;

public class Size {
    public static Size SIZE_UNKNOWN = new Size(0) {
        @Override
        public String getString(SizeFormatter formatter) {
            return "NaN";
        }
    };
    private final long value;

    public Size(@Range(from = 0, to = Long.MAX_VALUE) long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public String getString(SizeFormatter formatter) {
        return formatter.format(this.value);
    }
}
