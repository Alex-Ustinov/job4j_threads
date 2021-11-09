package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        String flag = "\\";
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Loading ... " + flag);
            try {
                if (flag.equals("\\")) {
                    flag = "|";
                } else if (flag.equals("|")) {
                    flag = "/";
                } else if (flag.equals("/")) {
                    flag = "\\";
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
