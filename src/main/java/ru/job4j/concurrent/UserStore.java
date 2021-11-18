package ru.job4j.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserStore {

    private volatile List<User> storage = new ArrayList();

    public boolean add (User user) {
        if (!storage.contains(user)) {
            synchronized (this) {
                if (!storage.contains(user)) {
                    storage.add(user);
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
        if (storage.contains(user)) {
            synchronized (this) {
                if (storage.contains(user)) {
                    return storage.remove(user);
                }
            }
            return false;
        }
        return false;
    }

    public Optional<User> findUser(Integer id) {
        return storage.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
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
