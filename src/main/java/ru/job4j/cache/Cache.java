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
        Base rsl = memory.computeIfPresent(model.getId(),
            (key, val) -> {
                Base stored = memory.get(val.getId());
                if (stored.getVersion() != val.getVersion()) {
                    throw new OptimisticException("Versions are not equal");
                }
                return new Base(val.getId(), val.getVersion() + 1);
            });
        return  rsl != null;
    }

    public void  delete(Base model) {
        memory.computeIfPresent(model.getId(), (key, val) -> memory.remove(key));
    }
}