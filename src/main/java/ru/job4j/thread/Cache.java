package ru.job4j.thread;

public class Cache {
    private static Cache cache;

    public static synchronized Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }

    public static synchronized Cache getCache() {
        return cache;
    }
}
