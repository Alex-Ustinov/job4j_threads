package ru.job4j.thread;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        Integer countInMoment = count.get();
        if (countInMoment == 0) {
            throw new UnsupportedOperationException("Count is not impl.");
        }
        count.compareAndSet(countInMoment, count.get() + 1);
    }

    public int get() {
        Integer rsl = count.get();
        if (rsl == 0) {
            throw new UnsupportedOperationException("Count is not impl.");
        }
        return rsl;
    }
}
