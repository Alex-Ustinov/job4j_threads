package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        String subject = "Notification " + user.getUsername() + " to email " + user.getEmail() + ".";
        String body = "Add a new event to " + user.getUsername();
        pool.submit(() -> send(subject, body, user.getEmail()));
    }

    private void close() {
        pool.shutdown();
    }

    public static void send(String subject, String body, String email) {

    }
    
}
