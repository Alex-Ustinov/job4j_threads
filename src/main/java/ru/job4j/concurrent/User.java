package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class User {
    private int id;
    private int amount;

    public User (Integer id, Integer amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void plusAmount(Integer money) {
            amount = amount + money;
    }

    public boolean minusAmount(Integer money) throws Exception {
        if (amount <= 0) {
            throw new Exception("User does not have enough money");
        }
        amount = amount - money;
        return true;
    }
}