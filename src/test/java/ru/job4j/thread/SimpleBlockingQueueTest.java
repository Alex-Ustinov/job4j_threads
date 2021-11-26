package ru.job4j.thread;

import org.junit.Test;
import ru.job4j.concurrent.SingleLockList;

import java.util.*;
import java.util.stream.IntStream;

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

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final List<Integer> buffer = new ArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> {
                    IntStream numbers = IntStream.range(0, 5);
                    try {
                        for (PrimitiveIterator.OfInt it = numbers.iterator(); it.hasNext(); ) {
                            Integer in = it.next();
                            queue.offer(in);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.getQueue().isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            Integer i = queue.poll();
                            System.out.println("I = " + i);
                            buffer.add(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        System.out.println(consumer.getState());
        consumer.join();
        System.out.println("! " + consumer.getState());
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }
}