package ru.job4j.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(),
            (key, val) -> {
                Base stored = memory.get(model.getId());
                if (stored.getVersion() != model.getVersion()) {
                    throw new OptimisticException("Versions are not equal");
                }
                Base base = memory.put(model.getId(), new Base(model.getId(), model.getVersion() + 1));
                return true;
            });

    }

    public void  delete(Base model) {
        memory.computeIfPresent(model.getId(), (key, val) -> memory.remove(key));
    }
}