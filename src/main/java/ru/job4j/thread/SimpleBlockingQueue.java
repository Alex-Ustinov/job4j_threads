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

    public void offer(T value) {
        synchronized (value) {
            queue.add(value);
            this.notifyAll();
        }
        synchronized (this) {
            while (queue.size() < capacity) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public T poll() {
        synchronized (Thread.currentThread()) {
            try {
                while (queue.size() == 0) {
                    Thread.currentThread().wait();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        synchronized (this) {
            if (queue.size() > 0) {
                return queue.poll();
            }
        }
        return null;
    }
}