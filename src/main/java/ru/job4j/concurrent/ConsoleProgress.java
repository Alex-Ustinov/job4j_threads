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
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("I do not see this print");
        progress.interrupt();
    }
}
