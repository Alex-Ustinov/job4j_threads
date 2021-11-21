package ru.job4j.concurrent;

import java.util.*;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class UserStore {
    @GuardedBy("this")
    private Map<Integer, User> storage = new HashMap<>();

    public synchronized boolean add (User user) {
        User result = storage.putIfAbsent(user.getId(), user);
        return checkIdUser(user, result);
    }
    public synchronized boolean update(User user) {
        User result = storage.replace(user.getId(), user);
        return checkIdUser(user, result);
    }

    public synchronized boolean delete(User user) {
        User result = storage.remove(user.getId());
        return checkIdUser(user, result);
    }

    public boolean checkIdUser(User initialUser, User receiveUser) {
        return initialUser.getId() == receiveUser.getId();
    }

    public void transfer(int fromId, int toId, int amount) throws Exception {
        User fromUser = storage.get(fromId);
        User toUser = storage.get(fromId);
            synchronized (fromUser) {
                synchronized (toUser) {
                    if (fromUser.getAmount() <= 0 || fromUser.getAmount() - amount < 0) {
                        throw new Exception("User does not have enough money");
                    }
                    toUser.setAmount(toUser.getAmount() + amount);
                    fromUser.setAmount(fromUser.getAmount() - amount);

                }
            }
    }
}
