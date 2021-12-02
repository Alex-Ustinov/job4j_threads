package ru.job4j.thread;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public CASCount(Integer count) {
        this.count.getAndSet(count);
    }

    public void increment() {
        Integer countInMoment;
        do {
            countInMoment = count.get();
        } while (!count.compareAndSet(countInMoment, countInMoment + 1));
    }

    public int get() {
        return  count.get();
    }
}
