package ru.job4j.concurrent;

import java.util.*;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class UserStore {
    @GuardedBy("this")
    private Map<Integer, User> storage = new HashMap<>();

    public boolean add (User user) {
        if (!storage.containsKey(user.getId())) {
            synchronized (this) {
                if (!storage.containsKey(user.getId())) {
                    storage.put(user.getId(), user);
                }
            }
        }
        return true;
    }
    public boolean update(User user) {
        Optional<User> changedUser = findUser(user.getId());
        if (changedUser.isPresent()) {
            synchronized (this) {
                if (changedUser.isPresent()) {
                    delete(changedUser.get());
                    add(user);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public boolean delete(User user) {
        if (!storage.containsKey(user.getId())) {
            synchronized (this) {
                if (!storage.containsKey(user.getId())) {
                    storage.remove(user.getId());
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public Optional<User> findUser(Integer id) {
        User findUser = storage.entrySet().stream()
                .filter(user -> user.getKey() == id)
                .findFirst().get().getValue();
        return new Optional(findUser);
    }

    public void transfer(int fromId, int toId, int amount) throws Exception {
        Optional<User> fromUserOption = findUser(fromId);
        Optional<User> toUserOption = findUser(toId);
        if (fromUserOption.isPresent() && toUserOption.isPresent()) {
            User fromUser = fromUserOption.get();
            User toUser = toUserOption.get();
            synchronized (fromUser) {
                synchronized (toUser) {
                    fromUser.minusAmount(amount);
                    toUser.plusAmount(amount);
                }
            }
        }
    }
}
