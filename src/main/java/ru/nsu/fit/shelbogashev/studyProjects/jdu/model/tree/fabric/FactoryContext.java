package ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.fabric;

import java.util.HashMap;

public class FactoryContext {
    private final HashMap<String, Object> data;

    public FactoryContext() {
        this.data = new HashMap<>();
    };

    public void put(String key, Object value) {
        data.put(key, value);
    }

    public Object get(String key) {
        return data.get(key);
    }

    public boolean contains(String key) {
        return data.containsKey(key);
    }
}
