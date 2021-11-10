package ru.job4j.concurrent;

public class ThreadTest {
    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            progress.interrupt();
            e.printStackTrace();
        }
    }
}
