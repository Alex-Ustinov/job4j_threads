package ru.job4j.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public void add(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName()));
    }

    public User findById(int id) {
        return User.of(users.get(id).getName());
    }

    public ConcurrentHashMap<Integer, User> getUsers() {
        return users;
    }

    public List<User> findAll() {
        ArrayList<User> result = new ArrayList<>();
        for (Map.Entry<Integer, User> user : users.entrySet()) {
            result.add(User.of(user.getValue().getName()));
        }
        return result;
    }
}
