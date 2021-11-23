package ru.job4j.thread;

import net.jcip.annotations.*;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private int capacity;

    SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void offer(T value) {
        while (queue.size() == capacity) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.add(value);
        this.notifyAll();
    }

    public synchronized T poll() {
        try {
            while (queue.size() == 0) {
                this.wait();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return queue.poll();
    }
}