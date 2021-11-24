package ru.job4j.thread;

import org.junit.Test;
import ru.job4j.concurrent.SingleLockList;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {
    @Test
    public void poll() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(5);

        Thread first = new Thread(() -> {
            for (int i = 0; i < 6; i++) {
                try {
                    queue.offer(i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        Thread second = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                try {
                    queue.poll();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        first.start();
        second.start();
        first.join();
        second.join();

        assertThat(queue.poll(), is(3));
    }
}