package ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.printer.model;

public class Limit {
    public static Limit UNLIMITED = new Limit(Long.MAX_VALUE);
    private final long n;

    private Limit(long n) {
        this.n = n;
    }

    public static Limit of(long n) {
        return new Limit(n);
    }

    public long value() {
        return n;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Limit)) return false;
        return ((Limit) obj).n == n;
    }
}
