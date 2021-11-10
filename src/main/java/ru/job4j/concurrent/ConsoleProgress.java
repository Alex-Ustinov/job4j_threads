package ru.job4j.concurrent;

import java.util.ArrayList;
import java.util.List;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        List<String> symbols = new ArrayList (List.of("\\", "|", "/"));
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (String symbol : symbols) {
                    Thread.sleep(500);
                    System.out.print("\r load: " + symbol);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
