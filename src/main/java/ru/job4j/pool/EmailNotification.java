package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        String subject = "Notification " + user.getUsername() + " to email " + user.getEmail() + ".";
        String body = "Add a new event to " + user.getUsername();
        pool.submit(() -> send(subject, body, user.getEmail()));
    }

    private void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void send(String subject, String body, String email) {

    }
    
}
