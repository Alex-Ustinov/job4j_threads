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
        if (!memory.containsKey(model.getId())) {
            return false;
        }
        Base stored = memory.get(model.getId());
        if (stored.getVersion() != model.getVersion()) {
            throw new OptimisticException("Versions are not equal");
        }
        do {
            memory.putIfAbsent(model.getId(), new Base(model.getId(), model.getVersion() + 1));
        } while (model.getVersion() + 1 == memory.get(model.getId()).getVersion());
        return true;
    }

    public void  delete(Base model) {
        memory.computeIfPresent(model.getId(), (key, val) -> memory.remove(key));
    }
    public static void main(String[] args) {
        Map<Integer, Base> map = new HashMap<>();
        Base base = new Base(1, 0);
        map.put(base.getId(), base);


        Base user1 = map.get(base.getId());
        user1.setName("User 1");

        Base user2 = map.get(base.getId());
        user1.setName("User 2");

        map.put(user1.getId(), user1);
        map.put(user2.getId(), user2);
        for (Map.Entry<Integer, Base> t : map.entrySet()) {
            System.out.println(t.getValue().getName());
        }

    }
}