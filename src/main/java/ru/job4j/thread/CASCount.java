package ru.job4j.thread;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    CASCount(Integer count) {
        this.count.getAndSet(count);
    }

    public void increment() {
        Integer countInMoment = count.get();
        do {
            if (countInMoment == 0) {
                throw new UnsupportedOperationException("Count is not impl.");
            }
            count.getAndSet(countInMoment + 1);
        } while (!count.compareAndSet(countInMoment, countInMoment + 1));
    }

    public int get() {
        return  count.get();
    }
}
