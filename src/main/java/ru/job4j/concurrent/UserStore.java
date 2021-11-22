package ru.job4j.concurrent;

import java.util.*;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class UserStore {
    @GuardedBy("this")
    private Map<Integer, User> storage = new HashMap<>();

    public synchronized User add (User user) {
        return storage.putIfAbsent(user.getId(), user);
    }
    public synchronized User update(User user) {
        return storage.replace(user.getId(), user);
    }

    public synchronized User delete(User user) {
        return storage.remove(user.getId());
    }

    public synchronized void transfer(int fromId, int toId, int amount) throws Exception {
        User fromUser = storage.get(fromId);
        User toUser = storage.get(fromId);
        if (fromUser != null && toUser != null) {
            if (fromUser.getAmount() <= 0 || fromUser.getAmount() - amount < 0) {
                throw new Exception("User does not have enough money");
            }
            if (fromUser.getAmount() - amount >= 0) {
                toUser.setAmount(toUser.getAmount() + amount);
                fromUser.setAmount(fromUser.getAmount() - amount);
            }
        }
    }
}
